package com.guanhong.foodie.post;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;

import java.util.ArrayList;

public interface PostContract {

    interface View extends BaseView<Presenter>{

        void setTabLayoutVisibility(boolean visible);

        void showAddress(String addressLine, LatLng latLng);

        void showPictures(ArrayList<String> stringArrayListExtra);

        void showNewPictures(ArrayList<String> newPictures);
    }

    interface Presenter extends BasePresenter{

        void showTabLayout();

        void hideTabLayout();

        void postArticle(Article article);

        void uploadImage(ArrayList<String> pictureList);
    }
}
