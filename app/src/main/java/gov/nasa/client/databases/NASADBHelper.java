package gov.nasa.client.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import gov.nasa.client.model.picture.PictureItemContract;

public class NASADBHelper extends SQLiteOpenHelper {
    private final String TAG = NASADBHelper.class.getSimpleName();
    public final static String DB_NAME = "nasa.db";
    public final static int VERSION = 1;

    public NASADBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate called. Creating tables...");
        PictureItemContract.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            default:
                onCreate(db);
        }
    }
}