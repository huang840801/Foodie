package com.guanhong.foodie.personal;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;

public interface PersonalContract {

    interface View extends BaseView<Presenter> {

        void showArticleUi(Article article);

        void setTabLayoutVisibility(boolean visible);

    }

    interface Presenter extends BasePresenter {

    }
}
