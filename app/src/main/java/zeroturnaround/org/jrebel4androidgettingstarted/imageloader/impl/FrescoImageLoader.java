package zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import jp.wasabeef.fresco.processors.BlurPostprocessor;
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

        Postprocessor blurPostprocessor = new BlurPostprocessor(imageView.getContext(), 10, 1) {

            @Override
            public void process(Bitmap dest, Bitmap source) {


                Canvas canvas = new Canvas(dest);


                Rect sourceRect = new Rect(0, 0, source.getWidth(), source.getHeight());
                RectF scaledDestRect = new RectF(0, 0, source.getWidth() - source.getWidth() / 4,
                        source.getHeight() - source.getHeight()/4);

                canvas.drawBitmap(source, sourceRect, scaledDestRect, null);

                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                matrix.preScale(-1, 1);
                matrix.postTranslate(0, scaledDestRect.height());
                canvas.setMatrix(matrix);

                scaledDestRect.offset(0, - scaledDestRect.height());
                canvas.drawBitmap(source, sourceRect, scaledDestRect, null);

                super.process(dest, dest);

                scaledDestRect.offset(0, 0);
                canvas.drawBitmap(source, sourceRect, scaledDestRect, null);
            }

        };

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).
                setPostprocessor(blurPostprocessor).build();

        PipelineDraweeController pipelineDraweeController = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().
                setImageRequest(imageRequest).setOldController(((SimpleDraweeView) imageView).getController()).build();

        ((SimpleDraweeView) imageView).setController(pipelineDraweeController);
    }
}
