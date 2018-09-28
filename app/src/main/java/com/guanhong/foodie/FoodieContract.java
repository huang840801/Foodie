package com.guanhong.foodie;

import com.guanhong.foodie.objects.Restaurant;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

//        void setTabLayoutVisibility();

        void showMapUi();

        void showLikedUi();

        void showLotteryUi();

        void showProfileUi();

        void showSearchUi();

        void showRestaurantUi(Restaurant restaurant);
    }

    interface Presenter extends BasePresenter {


        void transToMap();

        void transToLiked();

        void transToLotto();

        void transToProfile();

        void transToSearch();

        void tranToRestaurant(Restaurant restaurant);
    }
}
