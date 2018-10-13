package com.guanhong.foodie.search;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public interface SearchContract {

    interface View extends BaseView<Presenter>{

        void showSearchResult(ArrayList<Restaurant> restaurantArrayList);

        void showResultToast();

    }

    interface Presenter extends BasePresenter{

        void searchArticles(String s);
    }
}
