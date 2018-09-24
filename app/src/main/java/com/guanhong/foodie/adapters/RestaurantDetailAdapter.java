package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.restaurant.RestaurantContract;

public class RestaurantDetailAdapter extends RecyclerView.Adapter {

    private RestaurantContract.Presenter mPresenter;

    private Article mArticle;
    private Context mContext;

    public RestaurantDetailAdapter(Article article, RestaurantContract.Presenter presenter) {

        this.mArticle = article;
        this.mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
