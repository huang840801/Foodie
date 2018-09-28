package com.guanhong.foodie.liked;

import android.annotation.SuppressLint;
import android.util.Log;

import com.guanhong.foodie.util.Constants;

import static android.support.v4.util.Preconditions.checkNotNull;


public class LikedPresenter implements LikedContract.Presenter {

    private  LikedContract.View mLikedView;

    @Override
    public void start() {
        Log.d(Constants.TAG, "start: ");
    }

    @SuppressLint("RestrictedApi")
    public LikedPresenter(LikedContract.View likedView) {
        mLikedView = likedView;
        mLikedView.setPresenter(this);
    }
}
