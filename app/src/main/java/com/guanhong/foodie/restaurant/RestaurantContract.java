package com.guanhong.foodie.restaurant;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface RestaurantContract {

    interface View extends BaseView<Presenter> {

        void showPersonalArticleUi(Article article);

        void transToPost();

        void showRestaurantUi(Restaurant restaurant, ArrayList<Comment> comments);
    }

    interface Presenter extends BasePresenter {

        void openPersonalArticle(Article article);

        void transToPost();

        void setTabLayoutVisibility(boolean isTabLayoutVisibility);

        void transToPersonalArticle(Article article);

        void transToPostArticle();
    }
}

