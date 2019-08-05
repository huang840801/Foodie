package com.guanhong.foodie.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.custom.CircleCornerForm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendPhotoAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPicturesArrayList;

    public RecommendPhotoAdapter(ArrayList<String> restaurantPictures) {

        mPicturesArrayList = restaurantPictures;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_photo, parent, false);
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
                .error(R.drawable.photo_error_text)
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
