package com.guanhong.foodie.recommend;

import android.util.Log;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecommendPresenter implements RecommendContract.Presenter {

    private RecommendContract.View mRecommendView;

    public RecommendPresenter(RecommendContract.View recommendView) {
       mRecommendView= checkNotNull(recommendView, "mapView cannot be null");
       mRecommendView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d("Hello", " RecommendPresenter start");

    }
}
