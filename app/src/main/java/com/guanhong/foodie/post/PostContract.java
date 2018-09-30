package com.guanhong.foodie.post;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;

public interface PostContract {

    interface View extends BaseView<Presenter>{

        void setTabLayoutVisibility(boolean visible);

        void showAddress(String addressLine);
    }

    interface Presenter extends BasePresenter{

        void showTabLayout();

        void hideTabLayout();

        void postArticle(Article article);
    }
}
