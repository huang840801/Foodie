package com.guanhong.foodie.recommend;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;

public interface RecommendContract {


    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}