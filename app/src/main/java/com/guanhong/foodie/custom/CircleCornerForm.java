package com.guanhong.foodie.custom;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.util.BlurBitmapUtil;
import com.squareup.picasso.Transformation;


public class CircleCornerForm implements Transformation {

    private Context mContext;
    public CircleCornerForm(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = (float) (height / 20);   //角度


        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);
        output = BlurBitmapUtil.blurBitmap(mContext, bitmap, 20);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);   //填充背景
        paint.setColor(color);
        paint.setStrokeWidth(20);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); //兩圖交互顯示 mode (相交）
        canvas.drawBitmap(bitmap, rect, rect, paint);



        bitmap.recycle();
        return output;
    }

    @Override
    public String key() {
        return "roundcorner";
    }
}