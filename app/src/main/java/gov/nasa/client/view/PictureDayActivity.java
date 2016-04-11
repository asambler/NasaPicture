package gov.nasa.client.view;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import gov.nasa.client.model.picture.PictureItemContentProvider;
import gov.nasa.client.model.picture.PictureItemContract;
import gov.nasa.client.sync.SyncHelper;
import gov.nasa.client.view.adapters.SectionsPagerAdapter;

public class PictureDayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = SectionsPagerAdapter.class.getSimpleName();

    public static final int CURSOR_LOADER_ID = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_day);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SyncHelper.nextPicture();

        mViewPager = (ViewPager) findViewById(R.id.container);
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                PictureItemContract.PICTURE.CONTENT_URI,
                null,
                null,
                null,
                PictureItemContentProvider.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(mSectionsPagerAdapter == null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getBaseContext(), getSupportFragmentManager(), data);
            mViewPager.setAdapter(mSectionsPagerAdapter);
        } else {
            mSectionsPagerAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
