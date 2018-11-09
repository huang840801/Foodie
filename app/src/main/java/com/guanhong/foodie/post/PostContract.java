package com.guanhong.foodie.post;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.Base.BasePresenter;
import com.guanhong.foodie.Base.BaseView;
import com.guanhong.foodie.objects.Article;

import java.util.ArrayList;

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void showAddress(String addressLine, LatLng latLng);

        void showPictures(ArrayList<String> stringArrayListExtra);

        void showNewPictures(ArrayList<String> newPictures);

        void addPictures();

        void transToMap();

        void showErrorToast();
    }

    interface Presenter extends BasePresenter {


        void postArticle(Article article);

        void uploadImage(ArrayList<String> pictureList);

        void addPictures();

        void pickMultiplePictures();

        void transToMap();

        void transToPostChildMap();

        void checkRestaurantExists();

        void showErrorToast();
    }
}
