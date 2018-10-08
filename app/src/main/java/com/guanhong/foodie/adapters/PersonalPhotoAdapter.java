package com.guanhong.foodie.adapters;

import android.content.Context;
import android.graphics.Bitmap;
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

public class PersonalPhotoAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mPhotosList;
    private Context mContext;

    public PersonalPhotoAdapter(ArrayList<String> pictures) {
        mPhotosList = pictures;
        Log.d(Constants.TAG, "  showNewPictures " + mPhotosList);
        Log.d(Constants.TAG, "  showNewPictures " + mPhotosList.get(0));


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_personal_article_photo, parent, false);
//        return new PostArticlePhotoAdapter.PostPhotoItemViewHolder(view);

        return new PersonalPhotoAdapter.PersonalArticlePhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof PersonalArticlePhotoItemViewHolder){
            ((PersonalArticlePhotoItemViewHolder)holder).bindData(position);
        }

    }
//    if (holder instanceof PostPhotoItemViewHolder) {
//        ((PostPhotoItemViewHolder) holder).bindData(position);
//
//    }
    @Override
    public int getItemCount() {
        return mPhotosList.size();
    }

    private class PersonalArticlePhotoItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public PersonalArticlePhotoItemViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.imageView_personal_article_photo);

        }

//        public ImageView getImageView() {
//            return mImageView;
//        }

        public void bindData(int position) {
            Picasso.get()
                   .load(mPhotosList.get(position))
                    .error(R.drawable.all_picture_placeholder)
                   .into(mImageView);
        }
    }
}
