package com.guanhong.foodie;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

//        void setTabLayoutVisibility();

        void showMapUi();

        void showLikedUi();

        void showLotteryUi();

        void showProfileUi();

        void showSearchUi();

        void showRestaurantUi();
    }

    interface Presenter extends BasePresenter {

        void transToMap();

        void transToLiked();

        void transToLotto();

        void transToProfile();

        void transToSearch();

        void tranToDetail();
    }
}
