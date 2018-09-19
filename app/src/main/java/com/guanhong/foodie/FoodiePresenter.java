package com.guanhong.foodie;

import static android.support.v4.util.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.lottery.LotteryFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;


public class FoodiePresenter implements FoodieContract.Presenter, ViewPager.OnPageChangeListener {


    private FoodieContract.View mFoodieView;

    private LikedFragment mLikedFragment;
    private LotteryFragment mLotteryFragment;
    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mAdapter;

    private ArrayList<Fragment> mFragmentArrayList;


    @SuppressLint("RestrictedApi")
    public FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager, MyFragmentPagerAdapter adapter) {
        mFoodieView = checkNotNull(foodieView, "foodieView cannot be null!");
        mFoodieView.setPresenter(this);

        mFoodieView = foodieView;
        mViewPager = viewPager;
        mAdapter = adapter;

        init();
    }

    private void init() {
//        mFragmentArrayList.add(new LikedFragment());
//        mFragmentArrayList.add(new LotteryFragment());
//        mFragmentArrayList.add(new MapFragment());
//        mFragmentArrayList.add(new ProfileFragment());
//        mFragmentArrayList.add(new SearchFragment());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public void start() {
        transToMap();
    }

    public void transToMap() {

//        Log.d(Constants.TAG, "  transToMap");
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


//        Log.d(Constants.TAG, "  transToProfile");
        mViewPager.setCurrentItem(1);
        mFoodieView.showProfileUi();
    }



    @Override
    public void transToSearch() {
//        Log.d(Constants.TAG, "  transToSearch");
        mViewPager.setCurrentItem(2);
        mFoodieView.showSearchUi();
    }

    @Override
    public void transToLottery() {
//        Log.d(Constants.TAG, "  transToLottery");
        mViewPager.setCurrentItem(3);
        mFoodieView.showLotteryUi();
    }


    @Override
    public void transToLiked() {
//        Log.d(Constants.TAG, "  transToLiked");
        mViewPager.setCurrentItem(4);
        mFoodieView.showLikedUi();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mFoodieView.setButtonColor();
        mFoodieView.setCursor(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
