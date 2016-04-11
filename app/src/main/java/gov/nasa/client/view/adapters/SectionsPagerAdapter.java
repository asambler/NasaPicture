package gov.nasa.client.view.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import gov.nasa.client.model.picture.PictureItem;
import gov.nasa.client.sync.SyncHelper;
import gov.nasa.client.view.fragments.PlaceholderFragment;

public class SectionsPagerAdapter extends CursorFragmentPagerAdapter {

    public SectionsPagerAdapter(Context context, FragmentManager fm, Cursor cursor) {
        super(context, fm, cursor);
    }

    @Override
    public Fragment getItem(Context context, Cursor cursor) {
        PictureItem item = PictureItem.fromCursor(cursor);
        if (cursor.isLast()) {
            SyncHelper.pictureRequest(item.getDate());
        }
        return PlaceholderFragment.newInstance(item, cursor.getPosition());
    }

}
