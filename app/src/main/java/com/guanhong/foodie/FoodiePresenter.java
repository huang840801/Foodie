package com.guanhong.foodie;

import static android.support.v4.util.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.lottery.LotteryFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;


public class FoodiePresenter implements FoodieContract.Presenter {


    private FoodieContract.View mFoodieView;

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private LotteryFragment mLotteryFragment;
    private LikedFragment mLikedFragment;


    private MapPresenter mMapPresenter;

    private ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentArrayList;


    @SuppressLint("RestrictedApi")
    public FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager) {
//        mFoodieView = checkNotNull(foodieView, "foodieView cannot be null!");
        mFoodieView = foodieView;
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

//        if(mMapFragment == null){
//            Log.d(Constants.TAG, "  mMapFragment == null???");
//
//            mMapFragment = MapFragment.newInstance();
//        }

        if(mMapPresenter == null){
            Log.d(Constants.TAG, "  mMapPresenter == null???  " +mMapPresenter);

            mMapPresenter = new MapPresenter(MapFragment.newInstance());

            Log.d(Constants.TAG, "  mMapPresenter =   " +mMapPresenter);
        }

        mViewPager.setCurrentItem(0);
        mFoodieView.showMapUi();
    }

    @Override
    public void transToProfile() {
//        int i, total=0;
//        for(i=0 ; i<=10 ; i++){
//            total = total+i;
//        }
//        Log.d(Constants.TAG, "  i = " + total);


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

//        if(mLikedFragment == null){
//            Log.d(Constants.TAG, "  mLikeFragment == null???");
//
//        }
        mViewPager.setCurrentItem(4);
        mFoodieView.showLikedUi();
    }


}
