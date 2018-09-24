package com.guanhong.foodie.restaurant;

import android.annotation.SuppressLint;

import static android.support.v4.util.Preconditions.checkNotNull;

public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.View mRestaurantView;

    @Override
    public void showTabLayout() {
        mRestaurantView.setTabLayoutVisibility(true);

    }

    @Override
    public void hideTabLayout() {
        mRestaurantView.setTabLayoutVisibility(false);

    }

    @SuppressLint("RestrictedApi")
    public RestaurantPresenter(RestaurantContract.View restaurantView) {

        mRestaurantView = checkNotNull(restaurantView, "detailView cannot be null!");
        mRestaurantView.setPresenter(this);
    }

    @Override
    public void start() {
        mRestaurantView.showArticle();
    }
}
