package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guanhong.foodie.R;

public class RestaurantArticlePreviewAdapter extends RecyclerView.Adapter {

    private Context mContext;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant_article_preview, parent, false);
        return new ArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ArticleItemViewHolder) {
            bindMainItem((ArticleItemViewHolder) holder);
        }

    }

    private void bindMainItem(ArticleItemViewHolder holder) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class ArticleItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mArticlePhoto;

        public ArticleItemViewHolder(View view) {
            super(view);
            mArticlePhoto = view.findViewById(R.id.imageView_article_preview);
        }

        public ImageView getArticlePhoto() {
            return mArticlePhoto;
        }
    }
}
