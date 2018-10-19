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
import com.guanhong.foodie.custom.CircleCornerForm;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendPhotoAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPicturesArrayList;
    private Context mContext;

    public RecommendPhotoAdapter(ArrayList<String> restaurantPictures) {

        Log.d(Constants.TAG, "  restaurantPictures.size() = " + restaurantPictures.size());
        mPicturesArrayList = restaurantPictures;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_photo, parent, false);
        return new RecommendPhotoAdapter.RecommendPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecommendPhotoHolder) {
            bindMainItem((RecommendPhotoHolder) holder, position);
        }

    }

    private void bindMainItem(RecommendPhotoHolder holder, int position) {

        Picasso.get()
                .load(mPicturesArrayList.get(position))
                .fit()
                .transform(new CircleCornerForm())
                .placeholder(R.drawable.animated_rotate_drawable)
                .error(R.drawable.photo_error)
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mPicturesArrayList.size();
    }

    private class RecommendPhotoHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public RecommendPhotoHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.imageView_recommend_article_photo);
        }
    }
}
