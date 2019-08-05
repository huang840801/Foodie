package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guanhong.foodie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonalPhotoAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPictureList;

    public PersonalPhotoAdapter(ArrayList<String> pictures) {
        mPictureList = pictures;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_article_photo, parent, false);

        return new PersonalPhotoAdapter.PersonalArticlePhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PersonalArticlePhotoItemViewHolder) {
            ((PersonalArticlePhotoItemViewHolder) holder).bindData(position);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private class PersonalArticlePhotoItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public PersonalArticlePhotoItemViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.imageView_personal_article_photo);
        }

        public void bindData(int position) {
            int positionInPhoto = position % mPictureList.size();

            Picasso.get()
                    .load(mPictureList.get(positionInPhoto))
                    .fit()
                    .placeholder(R.drawable.animated_rotate_drawable)
                    .error(R.drawable.photo_error_text)
                    .into(mImageView);
        }
    }
}
