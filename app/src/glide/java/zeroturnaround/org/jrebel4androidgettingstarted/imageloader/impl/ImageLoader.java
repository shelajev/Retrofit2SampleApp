package zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Sten on 17/02/16.
 */
public class ImageLoader implements zeroturnaround.org.jrebel4androidgettingstarted.imageloader.ImageLoader {

    @Override
    public void loadImage(String url, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).transform(new BitmapTransformation(imageView.getContext()) {
            @Override
            protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                BlurTransformation blurTransformation = new BlurTransformation(imageView.getContext());
                Resource<Bitmap> blurredBitmapResource = blurTransformation.transform(BitmapResource.obtain(toTransform, pool), 10, 1);

                Bitmap combinedBitmap;
                Bitmap bottom = blurredBitmapResource.get();

                if ((combinedBitmap = pool.get(toTransform.getWidth(), bottom.getHeight() / 3 + toTransform.getHeight(), Bitmap.Config.ARGB_8888)) == null) {
                    combinedBitmap = Bitmap.createBitmap(toTransform.getWidth(), bottom.getHeight() / 3 + toTransform.getHeight(), toTransform.getConfig());
                }

                Canvas comboImage = new Canvas(combinedBitmap);
                comboImage.drawBitmap(toTransform, 0f, 0f, null);

                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                matrix.preScale(-1 , 1);
                matrix.postTranslate(0, toTransform.getHeight() * 2);

                comboImage.setMatrix(matrix);
                comboImage.drawBitmap(bottom, 0f, 0f, null);

                return BitmapResource.obtain(combinedBitmap, pool).get();
            }

            @Override
            public String getId() {
                return ImageLoader.class.getName() + ".Transformation";
            }
        }).into(imageView);
    }
}
