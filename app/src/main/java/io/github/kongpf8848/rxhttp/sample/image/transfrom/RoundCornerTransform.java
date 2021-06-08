package io.github.kongpf8848.rxhttp.sample.image.transfrom;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 圆角
 */
public class RoundCornerTransform extends BitmapTransformation {

    private static final String ID = "com.jsy.tk.library.image.transfrom.RoundCornerTransform";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private float[] radii = null;
    private ImageView.ScaleType mScaleType;

    public RoundCornerTransform(ImageView.ScaleType scaleType, int leftTop, int rightTop, int rightBottom, int leftBottom) {
        this.mScaleType=scaleType;
        this.radii = new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap source=null;
        if(mScaleType== ImageView.ScaleType.CENTER_CROP){
            source= TransformationUtils.centerCrop(pool,toTransform,outWidth,outHeight);
        }
        else if(mScaleType== ImageView.ScaleType.FIT_CENTER){
            source= TransformationUtils.fitCenter(pool,toTransform,outWidth,outHeight);
        }
        else {
            source=toTransform;
        }
        return roundCorner(pool, source);
    }

    private Bitmap roundCorner(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF rect = new RectF(0, 0, width, height);
        Path path = new Path();
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);

        return bitmap;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof RoundCornerTransform) {
            RoundCornerTransform other = (RoundCornerTransform) o;
            return Arrays.equals(radii,other.radii);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
