package gov.nasa.client.model.picture;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import gov.nasa.client.databases.NASADBHelper;

public class PictureItemContentProvider extends ContentProvider {

    public static final String AUTHORITY = "gov.nasa.client.model.picture";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String TAG = PictureItemContentProvider.class.getSimpleName();
    public static String DEFAULT_SORT_ORDER = PictureItemContract.PICTURE.COLUMN.DATE + " DESC";
    public static final int PICTURES_LIST = 1;
    public static final int PICTURES_ITEM = 2;

    static {
        URI_MATCHER.addURI(AUTHORITY, PictureItemContract.PATH.PICTURES_DATA, PICTURES_LIST);
        URI_MATCHER.addURI(AUTHORITY, PictureItemContract.PATH.PICTURES_DATA + "/#", PICTURES_ITEM);
    }

    private NASADBHelper dbHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        dbHelper = new NASADBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = URI_MATCHER.match(uri);
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
        String groupBy = null;
        switch (match) {
            case PICTURES_LIST:
                qBuilder.setTables(PictureItemContract.PICTURE.TABLE);
                break;
            case PICTURES_ITEM:
                qBuilder.setTables(PictureItemContract.PICTURE.TABLE);
                qBuilder.appendWhere(PictureItemContract.PICTURE.COLUMN._ID + "=");
                qBuilder.appendWhere(uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Invalid uri " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor ret = qBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);

        if (ret == null) {
            Log.i(TAG, "Query: failed. Uri: " + uri);
        } else {
            ret.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return ret;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case PICTURES_LIST:
                return PictureItemContract.PICTURE.CONTENT_TYPE;
            case PICTURES_ITEM:
                return PictureItemContract.PICTURE.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db;
        long rowId;
        Uri noteUri = null;

        switch (URI_MATCHER.match(uri)) {
            case PICTURES_LIST:
                db = dbHelper.getWritableDatabase();
                rowId = db.insertOrThrow(PictureItemContract.PICTURE.TABLE, null, values);
                noteUri = ContentUris.withAppendedId(PictureItemContract.PICTURE.CONTENT_URI, rowId);
                if (rowId < 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case PICTURES_ITEM:
                db = dbHelper.getWritableDatabase();
                rowId = db.insertOrThrow(PictureItemContract.PICTURE.TABLE, null, values);
                noteUri = ContentUris.withAppendedId(PictureItemContract.PICTURE.CONTENT_URI, rowId);
                if (rowId < 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new IllegalArgumentException("Unvalid URI: " + uri);
        }
        // notifyChange for every insert request
        getContext().getContentResolver().notifyChange(PictureItemContract.PICTURE.CONTENT_URI, null);
        return noteUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        String itemId;
        switch (URI_MATCHER.match(uri)) {
            case PICTURES_LIST:
                count = db.delete(PictureItemContract.PICTURE.TABLE, selection, selectionArgs);
                break;
            case PICTURES_ITEM:
                itemId = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(itemId)) {
                    if (TextUtils.isEmpty(selection)) {
                        selection = PictureItemContract.PICTURE.COLUMN._ID + "=?";
                        selectionArgs = new String[]{itemId};
                    } else {
                        selection = PictureItemContract.PICTURE.COLUMN._ID + "=?" + " AND (" + selection + ")";
                        List<String> oldArgs = Arrays.asList(selectionArgs);
                        oldArgs.add(0, itemId);
                        selectionArgs = oldArgs.toArray(new String[]{});
                    }

                }
                count = db.delete(PictureItemContract.PICTURE.TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unvalid URI: " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db;
        int count;
        String id;
        switch (URI_MATCHER.match(uri)) {
            case PICTURES_LIST:
                db = dbHelper.getWritableDatabase();
                count = db.update(PictureItemContract.PICTURE.TABLE, values, selection, selectionArgs);
                break;
            case PICTURES_ITEM:
                db = dbHelper.getWritableDatabase();
                id = uri.getLastPathSegment();
                selection = PictureItemContract.PICTURE.COLUMN._ID + "=" + id;
                count = db.update(PictureItemContract.PICTURE.TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid uri " + uri);
        }
        return count;
    }
}
