package com.guanhong.foodie.recommend;

import com.guanhong.foodie.Base.BasePresenter;
import com.guanhong.foodie.Base.BaseView;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface RecommendContract {

    interface View extends BaseView<Presenter> {

        void showAllRestaurantList(ArrayList<Restaurant> restaurantArrayList);
    }

    interface Presenter extends BasePresenter {

        void getMyRecommendRestaurant();
    }
}