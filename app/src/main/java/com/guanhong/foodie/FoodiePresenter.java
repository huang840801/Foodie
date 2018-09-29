package com.guanhong.foodie;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.liked.LikedPresenter;
import com.guanhong.foodie.lotto.LottoFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.post.PostFragment;
import com.guanhong.foodie.post.PostPresenter;
import com.guanhong.foodie.postchildmap.PostChildMapFragment;
import com.guanhong.foodie.postchildmap.PostChildMapPresenter;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.profile.ProfilePresenter;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;


public class FoodiePresenter implements FoodieContract.Presenter {


    private FoodieContract.View mFoodieView;
    private android.support.v4.app.FragmentManager mFragmentManager;

    private Context mContext;

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private LottoFragment mLottoFragment;
    private LikedFragment mLikedFragment;
    private RestaurantFragment mRestaurantFragment;
    private PostFragment mPostFragment;
    private PostChildMapFragment mPostChildMapFragment;


    private MapPresenter mMapPresenter;
    private ProfilePresenter mProfilePresenter;
    private RestaurantPresenter mRestaurantPresenter;
    private LikedPresenter mLikedPresenter;
    private PostPresenter mPostPresenter;
    private PostChildMapPresenter mPostChildMapPresenter;

    private ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentArrayList;


    public FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager, android.support.v4.app.FragmentManager supportFragmentManager, Context context) {
        mFoodieView = checkNotNull(foodieView, "foodieView cannot be null!");
//        mFoodieView = foodieView;
        mFoodieView.setPresenter(this);

//        mFoodieView = foodieView;
        mViewPager = viewPager;
        mFragmentManager = supportFragmentManager;
        mContext = context;
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

//        if(mProfileFragment == null){
//            mProfileFragment = ProfileFragment.newInstance();
//        }
//
//        mProfilePresenter = new ProfilePresenter(mProfileFragment, mContext);


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
//     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mRestaurantFragment == null) {
            mRestaurantFragment = RestaurantFragment.newInstance();
        }

        mRestaurantPresenter = new RestaurantPresenter(mRestaurantFragment, restaurant);

        fragmentTransaction.replace(R.id.fragment_container, mRestaurantFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        mFoodieView.showRestaurantUi(restaurant);


    }

    @Override
    public void transToPostArticle() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

        mPostPresenter = new PostPresenter(mPostFragment);

        fragmentTransaction.replace(R.id.fragment_container, mPostFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFoodieView.showPostArticleUi();
    }

    @Override
    public void transToPostChildMap() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostChildMapFragment == null) {
            mPostChildMapFragment = PostChildMapFragment.newInstance();
        }

        mPostChildMapPresenter = new PostChildMapPresenter(mPostChildMapFragment);

        fragmentTransaction.replace(R.id.fragment_container, mPostChildMapFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFoodieView.showPostChildMapUi();
    }

    @Override
    public void checkPostMapExist() {
        if (mPostChildMapFragment != null || mProfileFragment!=null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.remove(mPostChildMapFragment);
            fragmentTransaction.remove(mPostFragment);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void transToPostArticle(String addressLine) {
//        Bundle bundle = new Bundle();
//        bundle.putString("address", addressLine);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
//            mPostFragment.setArguments(bundle);
        }

        mPostPresenter = new PostPresenter(mPostFragment);
        mPostPresenter.setAddress(addressLine);

        fragmentTransaction.replace(R.id.fragment_container, mPostFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFoodieView.showPostArticleUi();
    }


}
