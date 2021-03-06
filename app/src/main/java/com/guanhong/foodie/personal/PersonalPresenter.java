package com.guanhong.foodie.personal;

import static com.google.common.base.Preconditions.checkNotNull;

import android.util.Log;

import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.Constants;


public class PersonalPresenter implements PersonalContract.Presenter {

    private PersonalContract.View mPersonalArticleView;
    private Article mArticle;

    private FoodieContract.Presenter mMainPresenter;

    public PersonalPresenter(PersonalContract.View personalArticleView, Article article) {
        mPersonalArticleView = checkNotNull(personalArticleView);
        mArticle = article;
        Log.d(Constants.TAG, "  PersonalPresenter " + mPersonalArticleView);
        Log.d(Constants.TAG, "  PersonalPresenter " + mArticle);
        mPersonalArticleView.setPresenter(this);

    }

    @Override
    public void start() {
        Log.d(Constants.TAG, " PersonalPresenter start");
        mPersonalArticleView.showArticleUi(mArticle);

    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }

    @Override
    public void checkRestaurantExists() {
        mMainPresenter.checkRestaurantExists();
    }
}
