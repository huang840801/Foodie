package com.guanhong.foodie.restaurant;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;

public interface RestaurantContract {

    interface View extends BaseView<Presenter> {

        void setTabLayoutVisibility(boolean visible);

        void showArticle();

    }

    interface Presenter extends BasePresenter {

       void showTabLayout();

       void hideTabLayout();


    }
}
