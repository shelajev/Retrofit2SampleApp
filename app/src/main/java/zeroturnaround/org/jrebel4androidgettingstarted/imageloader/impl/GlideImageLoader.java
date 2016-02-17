package zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.ImageLoader;

/**
 * Created by Sten on 17/02/16.
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
