package com.guanhong.foodie.personal_article;

import static com.google.common.base.Preconditions.checkNotNull;

public class PersonalArticlePresenter implements PersonalArticleContract.Presenter {

    private PersonalArticleContract.View mPersonalArticleView;

    public PersonalArticlePresenter(PersonalArticleContract.View personalArticleView){
        mPersonalArticleView = checkNotNull(personalArticleView);
        mPersonalArticleView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
