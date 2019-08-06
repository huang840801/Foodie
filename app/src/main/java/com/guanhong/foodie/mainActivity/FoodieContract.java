package com.guanhong.foodie.mainActivity;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

        void setTabLayoutVisibility(boolean isTabLayoutVisibility);

        void pickMultiplePictures();

        void transToPostChildMap();

        void pickSinglePicture();
    }

    interface Presenter extends BasePresenter {

        void transToMap();

        void transToLike();

        void transToRecommend();

        void transToProfile();

        void transToSearch();

        void transToRestaurant(Restaurant restaurant);

        void transToPostArticle();

        void transToPostArticle(String addressLine, LatLng latLng);

        void getPostRestaurantPictures(ArrayList<String> stringArrayListExtra);

        void transToPersonalArticle(Article article);

        void checkRestaurantExists();

        void pickMultiplePictures();

        void transToPostChildMap();

        void pickSinglePicture();

        void setTabLayoutVisibility(boolean isTabLayoutVisibility);
    }
}
