package gov.nasa.client.sync;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import gov.nasa.client.api.NASAApiAdapter;
import gov.nasa.client.model.picture.PictureItem;
import gov.nasa.client.model.picture.PictureItemContract;
import gov.nasa.client.utils.Util;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String TAG = SyncAdapter.class.getSimpleName();

    public static final int GET_DAY_PICS = 0xF012;
    public static final int GET_NEXT = 0x22;

    public static final String ACTION = "sync_adapter_action";
    public static final String DATE_ARGUMENT = "date_argument";

    public static final int PICTURES_REQUEST_NUMBERS = 7;


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }


    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync extras=[" + extras + "]");
        try {
            if(extras!= null) {
                int action = extras.getInt(ACTION);
                switch (action){
                    case GET_DAY_PICS:
                        getDayPics(extras.getString(DATE_ARGUMENT));
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception ex) {
            Log.d(TAG, "exception ex=[" + ex + "]");
        }

    }

    private void getDayPics(String date) {
        Log.d(TAG, "getDayPics date=[" + date + "]");
        LocalDate dateTime = Util.DAY_FORMATTER.parseLocalDate(date);
        for(int  i= 0; i < PICTURES_REQUEST_NUMBERS; i++) {
            String day = dateTime.minusDays(i).toString();
            //TODO : notify changing for every insert request (better use batch)
            Call<PictureItem> response = NASAApiAdapter.getRESTAdapter().pictureDay(day, true);
            response.enqueue(new PictureItemCallback());
        }
    }

    private String getToday() {
        return Util.DAY_FORMATTER.print(new DateTime());
    }

    private class PictureItemCallback implements Callback<PictureItem> {
        @Override
        public void onResponse(Response<PictureItem> response) {
            Log.d(TAG, "onResponse response=[" + response + "]");
            if (response.isSuccess()) {
                Log.d(TAG, "success response=[" + response.body() + "]");
                PictureItem pictureItem = response.body();
                if(!pictureItem.isEmpty()) {
                    ContentResolver contentResolver = getContext().getContentResolver();
                    //TODO : use applyBatch
                    Uri insertURI = contentResolver.insert(PictureItemContract.PICTURE.CONTENT_URI, pictureItem.getContentValues());
                    Log.d(TAG, "inserURI=[" + insertURI + "]");
                }
            } else {
                Log.d(TAG, "error response=[" + response.code() + "]");
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d(TAG,"onFailure trowable=[" + t + "]");
        }
    }
}