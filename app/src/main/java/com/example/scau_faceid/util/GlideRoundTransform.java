package com.example.scau_faceid.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

public class GlideRoundTransform extends BitmapTransformation {
    private static float radius = 0f;
    private float rotateRotationAngle = -90f;

    public GlideRoundTransform(){
        this(4);
    }

    public GlideRoundTransform(int dp){
        super();
        this.radius = Resources.getSystem().getDisplayMetrics().density*dp;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        //变换的时候裁切
        Matrix matrix = new Matrix();

        matrix.postRotate(rotateRotationAngle);

        Bitmap bitmap = Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        return roundCrop(pool, bitmap);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {}

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);//抗锯齿
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);//rect：RectF对象,rx：x方向上的圆角半径,ry：y方向上的圆角半径,paint：绘制时所使用的画笔
        //左上角、右上角圆角
        /*RectF rectRound = new RectF(0f, 100f,source.getWidth(), source.getHeight());
        canvas.drawRect(rectRound, paint);*/
        return result;
    }
}
