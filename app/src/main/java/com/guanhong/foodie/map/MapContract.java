package com.guanhong.foodie.map;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;

public interface MapContract {

    interface View extends BaseView<Presenter> {

        void showMap();
    }

    interface Presenter extends BasePresenter {

    }
}
