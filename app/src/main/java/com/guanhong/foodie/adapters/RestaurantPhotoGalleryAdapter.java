package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantPhotoGalleryAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPictureList;
    private Context mContext;


    public RestaurantPhotoGalleryAdapter(ArrayList<String> restaurantPictures) {
            this.mPictureList = restaurantPictures;
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

//        Log.d(Constants.TAG, "LLruCache imageview : " + ((RestaurantGalleryItemViewHolder) holder).getImagePictures());
//        Log.d(Constants.TAG, "LLruCache imageUrl : " + mPictures.get(position % mPictures.size()));


        if (holder instanceof RestaurantGalleryItemViewHolder) {
            ((RestaurantGalleryItemViewHolder)holder).bindData(position);

//            if(mPictures.size()>0){
//
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                ((FoodieActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//                ((RestaurantGalleryItemViewHolder) holder).getImagePictures()
//                        .setLayoutParams(new FrameLayout.LayoutParams(displayMetrics.widthPixels,
//                                mContext.getResources().getDimensionPixelSize(R.dimen.gallery_item_detail_height)));
//
//                ((RestaurantGalleryItemViewHolder) holder).getImagePictures()
//                        .setTag(mPictures.get(position % mPictures.size()));
//                new ImageFromLruCache().set(((RestaurantGalleryItemViewHolder) holder).getImagePictures(),
//                        mPictures.get(position % mPictures.size()));
//            }
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

//        public ImageView getImagePictures() {
//            return mImagePictures;
//        }

        public void bindData(int position) {
            int positionInPhoto = position % mPictureList.size();
            Picasso.get().load(mPictureList.get(positionInPhoto)).placeholder(R.drawable.all_picture_placeholder).into(mImagePictures);
        }
    }
}
