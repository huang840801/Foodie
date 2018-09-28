package com.guanhong.foodie.post;

import android.content.Context;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostPresenter implements PostContract.Presenter{

    private PostContract.View mPostView;

    private Context mContext;

    public PostPresenter(PostContract.View postView) {

        mPostView = checkNotNull(postView, "postView cannot be null");
        mPostView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void showTabLayout() {
        mPostView.setTabLayoutVisibility(true);

    }

    @Override
    public void hideTabLayout() {
        mPostView.setTabLayoutVisibility(false);

    }
}
