package com.guanhong.foodie.personal;

import com.guanhong.foodie.Base.BasePresenter;
import com.guanhong.foodie.Base.BaseView;
import com.guanhong.foodie.objects.Article;

public interface PersonalContract {

    interface View extends BaseView<Presenter> {

        void showArticleUi(Article article);

    }

    interface Presenter extends BasePresenter {

        void checkRestaurantExists();
    }
}
