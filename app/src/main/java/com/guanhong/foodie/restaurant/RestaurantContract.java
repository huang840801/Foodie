package com.guanhong.foodie.restaurant;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface RestaurantContract {

    interface View extends BaseView<Presenter> {

        void setTabLayoutVisibility(boolean visible);

        void showRestaurant(Restaurant restaurant, ArrayList<Comment> comments);
    }

    interface Presenter extends BasePresenter {

        void showTabLayout();

        void hideTabLayout();


    }
}
