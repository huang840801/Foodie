package com.guanhong.foodie.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.util.Constants;
import com.guanhong.foodie.util.ImageFromLruCache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RestaurantPhotoGalleryAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPictureList;
    private Context mContext;


    public RestaurantPhotoGalleryAdapter(ArrayList<String> restaurantPictures) {
        this.mPictureList = restaurantPictures;
        if (mPictureList.size() > 0) {
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant_top_photos, parent, false);
        return new RestaurantGalleryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RestaurantGalleryItemViewHolder) {
            ((RestaurantGalleryItemViewHolder) holder).bindData(position);

        }

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private class RestaurantGalleryItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImagePictures;

        public RestaurantGalleryItemViewHolder(View itemView) {
            super(itemView);

            mImagePictures = itemView.findViewById(R.id.imageView_restaurant_top);
        }


        public void bindData(int position) {
            int positionInPhoto = position % mPictureList.size();
            Log.d(Constants.TAG, "RestaurantPhotoGalleryAdapter: " + mPictureList.get(0));

//            Bitmap bitmap = null;
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(mPictureList.get(positionInPhoto), Base64.DEFAULT);
//            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//            mImagePictures.setImageBitmap(bitmap);
//            if (mPictureList.get(positionInPhoto).contains("storage")) {
//                Uri uri = Uri.fromFile(new File(mPictureList.get(positionInPhoto)));
//                String url = mPictureList.get(0);
//                try {
//                    URLEncoder.encode(url, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

//                Picasso.get().load(uri).placeholder(R.drawable.all_picture_placeholder).into(mImagePictures);
//            }else {
                Picasso.get()
                        .load(mPictureList.get(positionInPhoto))
                        .fit()
//                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.all_picture_placeholder)
                        .into(mImagePictures);

//            }
        }
    }
}
