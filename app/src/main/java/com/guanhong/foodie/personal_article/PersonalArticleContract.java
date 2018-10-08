package com.guanhong.foodie.personal_article;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;

public interface PersonalArticleContract {

    interface View extends BaseView<Presenter>{

        void showArticleUi(Article article);
    }

    interface Presenter extends BasePresenter{

    }
}
