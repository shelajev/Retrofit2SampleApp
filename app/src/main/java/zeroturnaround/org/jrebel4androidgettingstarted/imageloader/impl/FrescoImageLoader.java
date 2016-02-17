package zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl;

import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.ImageLoader;

/**
 * Created by Sten on 17/02/16.
 */
public class FrescoImageLoader implements ImageLoader {

    @Override
    public void loadImage(String url, ImageView imageView) {
        if (!(imageView instanceof SimpleDraweeView)) {
            return;
        }
        imageView.setImageURI(Uri.parse(url));

    }
}
