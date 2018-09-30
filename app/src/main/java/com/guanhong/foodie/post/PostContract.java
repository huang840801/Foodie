package com.guanhong.foodie.post;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;

import java.util.ArrayList;

public interface PostContract {

    interface View extends BaseView<Presenter>{

        void setTabLayoutVisibility(boolean visible);

        void showAddress(String addressLine);

        void showPictures(ArrayList<String> stringArrayListExtra);
    }

    interface Presenter extends BasePresenter{

        void showTabLayout();

        void hideTabLayout();

        void postArticle(Article article);
    }
}
