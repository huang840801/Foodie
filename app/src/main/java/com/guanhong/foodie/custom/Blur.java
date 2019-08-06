package com.guanhong.foodie.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.guanhong.foodie.util.BlurBitmapUtil;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Transformation;

public class Blur implements Transformation {

    private Context mContext;

    public Blur(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int left = 0;
        int top = 0;
        final float roundPx = (float) (height / 20);   //角度

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, width, height);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);   //填充背景
        paint.setColor(Constants.COLOR);
        paint.setStrokeWidth(20);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); //兩圖交互顯示 mode (相交）
        canvas.drawBitmap(bitmap, rect, rect, paint);

        output = BlurBitmapUtil.blurBitmap(mContext, bitmap, 20);

        bitmap.recycle();
        return output;
    }

    @Override
    public String key() {
        return Constants.BITMAP_ROUND_CORNER_KEY;
    }
}