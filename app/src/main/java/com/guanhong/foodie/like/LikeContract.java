package com.guanhong.foodie.like;

import com.guanhong.foodie.Base.BasePresenter;
import com.guanhong.foodie.Base.BaseView;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface LikeContract {

    interface View extends BaseView<Presenter> {

        void showLikeArticleList(ArrayList<Restaurant> restaurantArrayList);

    }

    interface Presenter extends BasePresenter {

        void transToRestaurant(Restaurant restaurant);
    }

}

