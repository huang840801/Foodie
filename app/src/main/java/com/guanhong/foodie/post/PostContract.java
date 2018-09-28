package com.guanhong.foodie.post;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;

public interface PostContract {

    interface View extends BaseView<Presenter>{

        void setTabLayoutVisibility(boolean visible);

    }

    interface Presenter extends BasePresenter{

        void showTabLayout();

        void hideTabLayout();
    }
}
