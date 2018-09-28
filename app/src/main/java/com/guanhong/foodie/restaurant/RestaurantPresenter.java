package com.guanhong.foodie.restaurant;

import android.util.Log;

import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import static com.google.common.base.Preconditions.checkNotNull;


public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.View mRestaurantView;
    private Restaurant mRestaurant;

    @Override
    public void showTabLayout() {
        mRestaurantView.setTabLayoutVisibility(true);

    }

    @Override
    public void hideTabLayout() {
        mRestaurantView.setTabLayoutVisibility(false);

    }

    public RestaurantPresenter(RestaurantContract.View restaurantView, Restaurant restaurant) {


        mRestaurantView = checkNotNull(restaurantView, "detailView cannot be null!");
        mRestaurantView.setPresenter(this);

        mRestaurant =restaurant;

        Log.d(Constants.TAG, " RestaurantPresenter  " + restaurant);
    }

    @Override
    public void start() {
        mRestaurantView.showRestaurant(mRestaurant);
    }
}
