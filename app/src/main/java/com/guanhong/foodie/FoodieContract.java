package com.guanhong.foodie;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

        void showMapUi();

        void showLikedUi();

        void showLotteryUi();

        void showProfileUi();

        void showSearchUi();


    }

    interface Presenter extends BasePresenter {

        void transToMap();

        void transToLiked();

        void transToLotto();

        void transToProfile();

        void transToSearch();
    }
}
