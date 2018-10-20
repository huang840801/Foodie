package com.guanhong.foodie;


import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface FoodieContract {

    interface View extends BaseView<Presenter> {

        void setTabLayoutVisibility(boolean isTabLayoutVisibility);

    }

    interface Presenter extends BasePresenter {

        void transToMap();

        void transToLike();

        void transToRecommend();

        void transToProfile();

        void transToSearch();

        void tranToRestaurant(Restaurant restaurant, ArrayList<Comment> comments);

        void transToPostArticle();

        void transToPostChildMap();

        void transToPostArticle(String addressLine, LatLng latLng);

        void getPostRestaurantPictures(ArrayList<String> stringArrayListExtra);

        void transToPersonalArticle(Article article);

        void checkRestaurantExists();


    }
}
