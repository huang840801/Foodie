package com.guanhong.foodie.personal_article;

import android.util.Log;

import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.Constants;

import static com.google.common.base.Preconditions.checkNotNull;

public class PersonalArticlePresenter implements PersonalArticleContract.Presenter {

    private PersonalArticleContract.View mPersonalArticleView;
    private Article mArticle;

    public PersonalArticlePresenter(PersonalArticleContract.View personalArticleView, Article article) {
        mPersonalArticleView = checkNotNull(personalArticleView);
        mArticle = article;
        Log.d(Constants.TAG, "  PersonalArticlePresenter " + mPersonalArticleView);
        Log.d(Constants.TAG, "  PersonalArticlePresenter " + mArticle);
        mPersonalArticleView.setPresenter(this);


    }

    @Override
    public void start() {
        Log.d(Constants.TAG, " PersonalArticlePresenter start");
        mPersonalArticleView.showArticleUi(mArticle);

    }
}
