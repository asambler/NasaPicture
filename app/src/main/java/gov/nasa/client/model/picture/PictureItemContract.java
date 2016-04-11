package gov.nasa.client.model.picture;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class PictureItemContract {

    public static final String AUTHORITY = PictureItemContentProvider.AUTHORITY;

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private static final String TAG = PictureItemContract.class.getSimpleName();

    public static void createTable(SQLiteDatabase db) {
        Log.i(TAG, " Creating tables ");
        db.execSQL("DROP TABLE IF EXISTS " + PICTURE.TABLE);
        db.execSQL(CREATE_TABLE_PICTURES);
    }


    interface PATH {
        String PICTURES_DATA = PICTURE.TABLE;
    }

    /**
     * TABLE PICTURE
     */
    public interface PICTURE {

        String TABLE = "pictures";

        class COLUMN {
            public static final String _ID = "_id";
            public static final String COPYRIGHT = "copyright";
            public static final String DATE = "date";
            public static final String EXPLANATION = "explanation";
            public static final String HD_URL = "hdurl";
            public static final String MEDIA_TYPE = "media_type";
            public static final String SERVICE_VERSION = "service_version";
            public static final String TITLE = "title";
            public static final String URL = "url";
            public static final String LOCAL_PATH = "local_path";

        }

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
                + BASE_CONTENT_URI + "." + PATH.PICTURES_DATA;

        // one row
        String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
                + AUTHORITY + "." + PATH.PICTURES_DATA;

        // common Uri
        Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/" + PATH.PICTURES_DATA);

    }

    private static String CREATE_TABLE_PICTURES = "CREATE TABLE " + PICTURE.TABLE
            + " ( " + PICTURE.COLUMN._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + PICTURE.COLUMN.COPYRIGHT + " TEXT , "
            + PICTURE.COLUMN.EXPLANATION + " TEXT , "
            + PICTURE.COLUMN.HD_URL + " TEXT , "
            + PICTURE.COLUMN.DATE + " TEXT  UNIQUE ON CONFLICT REPLACE , "
            + PICTURE.COLUMN.SERVICE_VERSION + " TEXT , "
            + PICTURE.COLUMN.TITLE + " TEXT , "
            + PICTURE.COLUMN.URL + " TEXT , "
            + PICTURE.COLUMN.LOCAL_PATH + " TEXT , "
            + PICTURE.COLUMN.MEDIA_TYPE + " TEXT );";
}
