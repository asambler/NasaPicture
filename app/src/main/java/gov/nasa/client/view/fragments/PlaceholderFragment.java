package gov.nasa.client.view.fragments;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import gov.nasa.client.model.picture.PictureItem;
import gov.nasa.client.view.R;

public class PlaceholderFragment extends Fragment implements View.OnLongClickListener {

    private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PICTURE_ITEM = "picture_item";

    private View rootView;
    private ImageView dayImageView;
    private PictureItem item;

    public static Fragment newInstance(PictureItem item, int position) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        args.putParcelable(ARG_PICTURE_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_picture_day, container, false);
        TextView date = (TextView) rootView.findViewById(R.id.section_date);
        TextView textView = (TextView) rootView.findViewById(R.id.section_description);
        TextView titleView = (TextView) rootView.findViewById(R.id.section_title);
        TextView copyrightView = (TextView) rootView.findViewById(R.id.section_copyright);
        dayImageView = (ImageView) rootView.findViewById(R.id.section_image);
        dayImageView.setOnLongClickListener(this);
        Bundle argument = getArguments();
        item = argument != null ? argument.<PictureItem>getParcelable(ARG_PICTURE_ITEM) : null;
        if (item != null) {
            Log.d(TAG, "onCreateView item=[" + item + "]");
            date.setText(item.getDate());
            titleView.setText(item.getTitle());
            textView.setText(item.getExplanation());
            if(item.getCopyright() != null) {
                copyrightView.setText("@Copyright " + item.getCopyright());
            }
            if (item.isImage()) {
                Picasso.with(getContext())
                        .load(item.getUrl())
                        .into(dayImageView);
            }
        }
        return rootView;
    }

    @Override
    public boolean onLongClick(View v) {
        Snackbar.make(v, "Set the picture as wallpaper", Snackbar.LENGTH_LONG)
                .setAction("Set", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getContext());
                        if (item != null) {
                            Toast.makeText(getContext(), "Wait a moment!", Toast.LENGTH_SHORT).show();
                            Picasso.with(getContext())
                                    .load(item.getUrl()) // or HD
                                    .into(new WallpaperTarget(myWallpaperManager));
                        }
                    }
                }).show();
        return false;
    }

    private class WallpaperTarget implements Target {
        private final WallpaperManager myWallpaperManager;

        public WallpaperTarget(WallpaperManager myWallpaperManager) {
            this.myWallpaperManager = myWallpaperManager;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.d(TAG, "onBitmapLoaded " + from);
            try {
                myWallpaperManager.setBitmap(bitmap);
                Toast.makeText(getContext(), "Success, image set as wallpaper!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getContext(), "Sorry! Can't image set as wallpaper!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Toast.makeText(getContext(), "Image loading failed!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
