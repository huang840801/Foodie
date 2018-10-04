package com.guanhong.foodie.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.custom.CircleCornerForm;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.BlurBitmapUtil;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProfileArticleAdapter extends RecyclerView.Adapter {

    private ArrayList<Article> mArticleArrayList;
    private ArrayList<Bitmap> mBitmapList;
    private Context mContext;
    private Typeface mTypeface;


    public ProfileArticleAdapter(ArrayList<Article> articleArrayList) {
        mArticleArrayList = articleArrayList;
        mBitmapList = new ArrayList<>();
        Log.d(Constants.TAG, " mArticleArrayList " + mArticleArrayList.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_profile_article, parent, false);
        return new ProfileArticleAdapter.ProfileArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProfileArticleHolder) {
            bindArticleItem((ProfileArticleHolder) holder, position);
        }
    }

    private void bindArticleItem(final ProfileArticleHolder holder, int position) {
        holder.getRestaurantName().setText(mArticleArrayList.get(position).getRestaurantName());
        holder.getContent().setText(mArticleArrayList.get(position).getContent());


//        Bitmap bitmap = (BitmapFactory.decodeFile(mArticleArrayList.get(position).getPictures().get(0), getBitmapOption(2)));
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        int newWidth = 160;
//        int newHeight = 90;
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//
//        bitmap = BlurBitmapUtil.blurBitmap(mContext, bitmap, 20);
//
//        bitmap = newBitmap(bitmap);


//        holder.getImageView().setImageBitmap(bitmap);



        Picasso.get()
                .load(mArticleArrayList.get(position).getPictures().get(0))
                .resize(600,300)
                .transform(new CircleCornerForm(mContext))
                .centerInside()
                .into(holder.getImageView());
    }

    private Bitmap newBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int left = 0, top = 0, right = width, bottom = height;
//        int left = width, top = height, right = width, bottom = height;

        float roundPx = (float) (height / 10);   //角度
//        if (width > height) {
//            left = (width - height) / 2;
//            top = 0;
//            right = left + height;
//            bottom = height;
//        } else if (height > width) {
//            left = 0;
//            top = (height - width) / 2;
//            right = width;
//            bottom = top + width;
//            roundPx = width / 2;
//        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);   //填充背景
        paint.setColor(color);
        paint.setStrokeWidth(20);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); //兩圖交互顯示 mode (相交）
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

    private BitmapFactory.Options getBitmapOption(int i) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = i;
        return options;
    }

    @Override
    public int getItemCount() {
        return mArticleArrayList.size();
    }

    private class ProfileArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mRestaurantName;
        private TextView mContent;


        public ProfileArticleHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.imageView_profile_article_photo);
            mRestaurantName = view.findViewById(R.id.textView_profile_article_retaurantName);
            mContent = view.findViewById(R.id.textView_profile_article_content);

            view.setOnClickListener(this);

            setTypeFace();

        }

        private void setTypeFace() {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/GenJyuuGothicX-Bold.ttf");

            mRestaurantName.setTypeface(mTypeface);
            mContent.setTypeface(mTypeface);
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public TextView getRestaurantName() {
            return mRestaurantName;
        }

        public TextView getContent() {
            return mContent;
        }

        @Override
        public void onClick(View view) {

        }
    }

}
