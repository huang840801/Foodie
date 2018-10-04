package com.guanhong.foodie;


import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

        void setTabLayoutVisibility(boolean isTabLayoutVisibility);

        void showMapUi();

        void showLikedUi();

        void showLotteryUi();

        void showProfileUi();

        void showSearchUi();

        void showRestaurantUi(Restaurant restaurant);

        void showPostArticleUi();

        void showPostChildMapUi();
    }

    interface Presenter extends BasePresenter {


        void transToMap();

        void transToLiked();

        void transToLotto();

        void transToProfile();

        void transToSearch();

        void tranToRestaurant(Restaurant restaurant);

        void transToPostArticle();

        void transToPostChildMap();

        void checkPostMapExist();

        void transToPostArticle(String addressLine, LatLng latLng);

        void getPostRestaurantPictures(ArrayList<String> stringArrayListExtra);
    }
}
