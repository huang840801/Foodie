package com.guanhong.foodie;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.liked.LikedPresenter;
import com.guanhong.foodie.lotto.LottoFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;


public class FoodiePresenter implements FoodieContract.Presenter {


    private FoodieContract.View mFoodieView;

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private LottoFragment mLottoFragment;
    private LikedFragment mLikedFragment;


    private MapPresenter mMapPresenter;
    private RestaurantPresenter mRestaurantPresenter;
    private LikedPresenter mLikedPresenter;

    private ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentArrayList;


    public FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager) {
        mFoodieView = checkNotNull(foodieView, "foodieView cannot be null!");
//        mFoodieView = foodieView;
        mFoodieView.setPresenter(this);

//        mFoodieView = foodieView;
        mViewPager = viewPager;

        init();
    }

    private void init() {

//        mViewPager.setAdapter(mAdapter);


    }

    @Override
    public void start() {
        transToMap();
    }

    public void transToMap() {

        Log.d(Constants.TAG, "  transToMap");

        mViewPager.setCurrentItem(0);
        mFoodieView.showMapUi();
    }

    @Override
    public void transToProfile() {

        Log.d(Constants.TAG, "  transToProfile");
        mViewPager.setCurrentItem(1);
        mFoodieView.showProfileUi();
    }

    @Override
    public void transToSearch() {
        Log.d(Constants.TAG, "  transToSearch");
        mViewPager.setCurrentItem(2);
        mFoodieView.showSearchUi();
    }



    @Override
    public void transToLotto() {
        Log.d(Constants.TAG, "  transToLotto");
        mViewPager.setCurrentItem(3);
        mFoodieView.showLotteryUi();
    }


    @Override
    public void transToLiked() {
        Log.d(Constants.TAG, "  transToLiked");

//        LikedFragment likedFragment = LikedFragment.newInstance();
//        mLikedPresenter = new LikedPresenter(likedFragment);

        mViewPager.setCurrentItem(4);
        mFoodieView.showLikedUi();
    }

    @Override
    public void tranToRestaurant(Restaurant restaurant) {
        Log.d("restaurant ", " FoodiePresenter : " + restaurant);


        mFoodieView.showRestaurantUi(restaurant);



    }

    @Override
    public void transToPostArticle() {
        mFoodieView.showPostArticleUi();
    }
}
