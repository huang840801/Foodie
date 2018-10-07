package com.guanhong.foodie;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.liked.LikedPresenter;
import com.guanhong.foodie.lotto.LottoFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Comment;
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

    public static final String MAP = "MAP";
    public static final String PROFILE = "PROFILE";
    public static final String SEARCH = "SEARCH";
    public static final String LOTTO = "LOTTO";
    public static final String LIKE = "LIKE";
    public static final String RESTAURANT = "RESTAURANT";
    public static final String POST = "POST";
    public static final String POSTCHILDMAP = "POSTCHILDMAP";

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


    public FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager, android.support.v4.app.FragmentManager supportFragmentManager) {
        mFoodieView = checkNotNull(foodieView, "foodieView cannot be null!");
//        mFoodieView = foodieView;
        mFoodieView.setPresenter(this);

//        mFoodieView = foodieView;
        mViewPager = viewPager;
        mFragmentManager = supportFragmentManager;
    }


    @Override
    public void start() {
        transToMap();
    }

    public void transToMap() {

        Log.d(Constants.TAG, "  transToMap");
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPostFragment != null && !mPostFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
            fragmentTransaction.remove(mPostFragment);
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
            fragmentTransaction.remove(mRestaurantFragment);
        }
        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
            fragmentTransaction.remove(mPostChildMapFragment);
        }
        fragmentTransaction.commit();

        mViewPager.setCurrentItem(0);
        mFoodieView.showMapUi();
    }

    @Override
    public void transToProfile() {

        Log.d(Constants.TAG, "  transToProfile");
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPostFragment != null && !mPostFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mPostFragment);
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mRestaurantFragment);

        }
        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mPostChildMapFragment);
        }
        fragmentTransaction.commit();

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
    public void tranToRestaurant(Restaurant restaurant, ArrayList<Comment> comments) {
        Log.d("restaurant ", " FoodiePresenter : " + restaurant);
        mFoodieView.setTabLayoutVisibility(true);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();


        if (mRestaurantFragment == null) {
            mRestaurantFragment = RestaurantFragment.newInstance();
        }

        mRestaurantPresenter = new RestaurantPresenter(mRestaurantFragment, restaurant, comments);

        if (!mRestaurantFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, mRestaurantFragment, RESTAURANT);
        } else {
            fragmentTransaction.show(mRestaurantFragment);
        }
        fragmentTransaction.commit();

        mFoodieView.showRestaurantUi();


    }

    @Override
    public void transToPostArticle() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

        if (mPostFragment != null && !mPostFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mPostFragment);
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mRestaurantFragment);

        }
        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mPostChildMapFragment);
//            mFoodieView.setTabLayoutVisibility(true);
        }

        mPostPresenter = new PostPresenter(mPostFragment);

//        if (!mPostFragment.isAdded()) {
//            fragmentTransaction.add(R.id.fragment_container, mPostFragment, POST);
//            fragmentTransaction.show(mPostFragment);
//
//        } else {
//            fragmentTransaction.show(mPostFragment);
//        }
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
        if (mPostFragment != null && !mPostFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mPostFragment);
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mRestaurantFragment);

        }
        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
            fragmentTransaction.remove(mPostChildMapFragment);
        }
        mPostChildMapPresenter = new PostChildMapPresenter(mPostChildMapFragment);

//        if (!mPostChildMapFragment.isAdded()) {
//            fragmentTransaction.add(R.id.fragment_container, mPostChildMapFragment, POSTCHILDMAP);
//        } else {
//            fragmentTransaction.show(mPostChildMapFragment);
//        }
        fragmentTransaction.replace(R.id.fragment_container, mPostChildMapFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFoodieView.showPostChildMapUi();
    }

    @Override
    public void checkPostMapExist() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPostChildMapFragment != null || mProfileFragment != null) {
            fragmentTransaction.remove(mPostChildMapFragment);
        }
        if (mPostFragment != null || !mPostFragment.isHidden()) {
            Log.d(Constants.TAG, "   mPostFragment isHidden");

            fragmentTransaction.hide(mPostFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void transToPostArticle(String addressLine, LatLng latLng) {
//        Bundle bundle = new Bundle();
//        bundle.putString("address", addressLine);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
//            mPostFragment.setArguments(bundle);
        }

        mPostPresenter = new PostPresenter(mPostFragment);
        mPostPresenter.setAddress(addressLine, latLng);

        fragmentTransaction.replace(R.id.fragment_container, mPostFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFoodieView.showPostArticleUi();
    }

    @Override
    public void getPostRestaurantPictures(ArrayList<String> stringArrayListExtra) {
        mPostPresenter.getPictures(stringArrayListExtra);
    }


}
