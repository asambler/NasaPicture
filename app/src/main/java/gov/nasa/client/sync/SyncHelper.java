package gov.nasa.client.sync;

import android.content.ContentResolver;
import android.os.Bundle;

import org.joda.time.DateTime;

import gov.nasa.client.model.picture.PictureItemContentProvider;
import gov.nasa.client.utils.Util;

public class SyncHelper {

    public static void pictureRequest(String date) {
        Bundle bundle = new Bundle();
        bundle.putInt(SyncAdapter.ACTION, SyncAdapter.GET_DAY_PICS);
        bundle.putString(SyncAdapter.DATE_ARGUMENT, date);
        ContentResolver.requestSync(null, PictureItemContentProvider.AUTHORITY, bundle);
    }

    public static void nextPicture() {
        String today = Util.DAY_FORMATTER.print(new DateTime());
        Bundle bundle = new Bundle();
        bundle.putInt(SyncAdapter.ACTION, SyncAdapter.GET_DAY_PICS);
        bundle.putString(SyncAdapter.DATE_ARGUMENT, today);
        ContentResolver.requestSync(null, PictureItemContentProvider.AUTHORITY, bundle);
    }
}
