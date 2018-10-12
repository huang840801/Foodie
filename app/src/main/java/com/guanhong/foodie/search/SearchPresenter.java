package com.guanhong.foodie.search;

import android.util.Log;
import com.orhanobut.logger.Logger;
import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter implements SearchContract.Presenter{

    private SearchContract.View mSearchView;

    public SearchPresenter(SearchContract.View searchView) {
        mSearchView = checkNotNull(searchView, "searchView cannot be null");
        mSearchView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d("Hello", " SearchPresenter start");
        Logger.d("hellohuang");
    }
}
