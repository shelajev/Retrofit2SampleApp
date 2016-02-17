package zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.ImageLoader;

/**
 * Created by Sten on 17/02/16.
 */
public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImage(String url,final ImageView imageView) {
        Picasso.with(imageView.getContext()).load(url).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap combinedBitmap;
                combinedBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight() / 3 + source.getHeight(), source.getConfig());

                Canvas combinedCanvas = new Canvas(combinedBitmap);
                combinedCanvas.drawBitmap(source, 0f, 0f, null);

                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                matrix.preScale(-1, 1);
                matrix.postTranslate(0, source.getHeight() * 2);

                BlurTransformation blurTransformation = new BlurTransformation(imageView.getContext(), 10, 1);
                Bitmap bottom = blurTransformation.transform(source);

                combinedCanvas.setMatrix(matrix);
                combinedCanvas.drawBitmap(bottom, 0f, 0f, null);
                return combinedBitmap;
            }

            @Override
            public String key() {
                return PicassoImageLoader.class.getName() + ".Transformation";
            }
        }).into(imageView);
    }
}
