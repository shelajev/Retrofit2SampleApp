package zeroturnaround.org.jrebel4androidgettingstarted.imageloader;

import android.widget.ImageView;

/**
 * Just a wrapper to hide 3rd party image loading library implementation details
 * Created by Sten on 17/02/16.
 */
public interface ImageLoader {
    void loadImage(String url, ImageView imageView);
}
