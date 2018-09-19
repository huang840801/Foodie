package com.guanhong.foodie;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

        void showMapUi();

        void showLikedUi();

        void showLotteryUi();

        void showProfileUi();

        void showSearchUi();

        void setButtonColor();

        void setCursor(int i);
    }

    interface Presenter extends BasePresenter {

        void transToMap();

        void transToLiked();

        void transToLottery();

        void transToProfile();

        void transToSearch();
    }
}
