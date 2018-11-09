package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantPhotoGalleryAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPictureList;


    public RestaurantPhotoGalleryAdapter(ArrayList<String> restaurantPictures) {
        this.mPictureList = restaurantPictures;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_top_photos, parent, false);
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

            Picasso.get()
                    .load(mPictureList.get(positionInPhoto))
                    .fit()
                    .placeholder(R.drawable.animated_rotate_drawable)
                    .error(R.drawable.photo_error_text)
                    .into(mImagePictures);

        }
    }
}
