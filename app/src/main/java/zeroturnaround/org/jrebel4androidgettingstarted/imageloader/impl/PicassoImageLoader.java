package zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.ImageLoader;

/**
 * Created by Sten on 17/02/16.
 */
public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImage(String url, ImageView imageView) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
