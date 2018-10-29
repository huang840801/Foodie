package com.guanhong.foodie.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.post.PostContract;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostArticlePhotoAdapter extends RecyclerView.Adapter {

    private PostContract.Presenter mPresenter;

    private ArrayList<String> mPhotosList;
    private ArrayList<Bitmap> mBitmapList;

    public PostArticlePhotoAdapter(ArrayList<String> stringArrayListExtra, PostContract.Presenter presenter) {
        mPhotosList = stringArrayListExtra;
        mBitmapList = new ArrayList<>();
        mPresenter = presenter;
//        Log.d(Constants.TAG, "  mPhotosList " + mPhotosList.get(0));

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_restaurant_photo, parent, false);
        return new PostArticlePhotoAdapter.PostPhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostPhotoItemViewHolder) {
            ((PostPhotoItemViewHolder) holder).bindData(position);

        }
    }

    @Override
    public int getItemCount() {
        if (mPhotosList.size() == 0) {
            Log.d(Constants.TAG, "  mPhotosList = 0");

            return 1;
        } else {
            Log.d(Constants.TAG, "  mPhotosList " + mPhotosList.size());

            return mPhotosList.size();
        }
    }

    private class PostPhotoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;

        public PostPhotoItemViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.imageView_post_photo);
            view.setOnClickListener(this);
        }

        public void bindData(int position) {
            for (int i = 0; i < mPhotosList.size(); i++) {


                Bitmap bitmap = (BitmapFactory.decodeFile(mPhotosList.get(i), getBitmapOption(2)));
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int newWidth = 160;
                int newHeight = 90;
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

                bitmap = newBitmap(bitmap);

                mBitmapList.add(bitmap);
            }
            mImageView.setImageBitmap(mBitmapList.get(position));

        }

        @Override
        public void onClick(View view) {
            mPresenter.addPictures();
        }
    }

    private Bitmap newBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int left = 0;
        int top = 0;

        final float roundPx = (float) (height / 10);   //角度

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, width, height);
        final RectF rectF = new RectF(rect);

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
}
