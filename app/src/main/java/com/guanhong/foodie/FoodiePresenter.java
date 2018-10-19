package com.guanhong.foodie;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.like.LikeFragment;
import com.guanhong.foodie.like.LikePresenter;
import com.guanhong.foodie.recommend.RecommendFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.personal_article.PersonalArticleFragment;
import com.guanhong.foodie.personal_article.PersonalArticlePresenter;
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
    public static final String POST_CHILD_MAP = "POSTCHILDMAP";
    public static final String PERSONAL_ARTICLE = "PERSONALARTICLE";

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private RecommendFragment mRecommendFragment;
    private LikeFragment mLikeFragment;
    private RestaurantFragment mRestaurantFragment;
    private PostFragment mPostFragment;
    private PostChildMapFragment mPostChildMapFragment;
    private PersonalArticleFragment mPersonalArticleFragment;


    private MapPresenter mMapPresenter;
    private ProfilePresenter mProfilePresenter;
    private RestaurantPresenter mRestaurantPresenter;
    private LikePresenter mLikePresenter;
    private PostPresenter mPostPresenter;
    private PostChildMapPresenter mPostChildMapPresenter;
    private PersonalArticlePresenter mPersonalArticlePresenter;

    private ViewPager mViewPager;

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

//        Log.d(Constants.TAG, "  transToMap");
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        if (mMapFragment == null) {
//            mMapFragment = MapFragment.newInstance();
//        }
//        if (!mMapFragment.isAdded()) {
//            fragmentTransaction.add(R.id.fragment_container, mMapFragment, MAP);
//        } else {
//            fragmentTransaction.show(mMapFragment);
//        }
//
//        if (mPostFragment != null && !mPostFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
//            fragmentTransaction.remove(mPostFragment);
//        }
//        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
//            fragmentTransaction.remove(mRestaurantFragment);
//        }
//        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
//            fragmentTransaction.remove(mPostChildMapFragment);
//        }
//        fragmentTransaction.commit();

        mViewPager.setCurrentItem(0);
        mFoodieView.showMapUi();
    }

    @Override
    public void transToProfile() {

        Log.d(Constants.TAG, "  transToProfile");
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

//
//        if (mPostFragment != null && !mPostFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.remove(mPostFragment);
//        }
//        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.remove(mRestaurantFragment);
//
//        }
//        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
//        if (mPostChildMapFragment != null ) {
//            Log.d(Constants.TAG, "  transToProfile mPostChildMapFragment isHidden");
//            fragmentTransaction.remove(mPostChildMapFragment);
//            mFragmentManager.popBackStack();
//        }
//        fragmentTransaction.commit();

        mViewPager.setCurrentItem(4);
        mFoodieView.showProfileUi();
    }

    @Override
    public void transToSearch() {
        Log.d(Constants.TAG, "  transToSearch");

        mViewPager.setCurrentItem(1);
        mFoodieView.showSearchUi();
    }


    @Override
    public void transToRecommend() {
        Log.d(Constants.TAG, "  transToRecommend");
        mViewPager.setCurrentItem(3);
        mFoodieView.showLotteryUi();
    }

    @Override
    public void transToLike() {
        Log.d(Constants.TAG, "  transToLiked");
//        LikeFragment likedFragment = LikeFragment.newInstance();
//        mLikePresenter = new LikePresenter(likedFragment);
        mViewPager.setCurrentItem(2);
        mFoodieView.showLikedUi();
    }

    @Override
    public void tranToRestaurant(Restaurant restaurant, ArrayList<Comment> comments) {
        Log.d("restaurant ", " FoodiePresenter : " + restaurant);
//        mFoodieView.setTabLayoutVisibility(false);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.emerge, R.anim.emerge);
        if (mRestaurantFragment == null) {
            mRestaurantFragment = RestaurantFragment.newInstance();
        }

        mRestaurantPresenter = new RestaurantPresenter(mRestaurantFragment, restaurant, comments);

        if (!mRestaurantFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, mRestaurantFragment, RESTAURANT);
        } else {
            fragmentTransaction.show(mRestaurantFragment);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        mFoodieView.showRestaurantUi();

    }

    @Override
    public void transToPostArticle() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

//        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.hide(mPostChildMapFragment);
//        }
//        if (mMapFragment != null && !mMapFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.hide(mMapFragment);
//            mFoodieView.setTabLayoutVisibility(true);
//        }
        mPostPresenter = new PostPresenter(mPostFragment);

        if (!mPostFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, mPostFragment, POST);
        } else {
            fragmentTransaction.show(mPostFragment);
        }
//        fragmentTransaction.replace(R.id.fragment_container, mPostFragment, "");
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

//        if (mMapFragment != null && !mMapFragment.isHidden()) {
//        Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.hide(mMapFragment);
//            mFoodieView.setTabLayoutVisibility(true);
//        }
        if (mMapFragment != null && !mMapFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToProfile mMapFragment isHidden");
            fragmentTransaction.remove(mMapFragment);
        }
//        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.remove(mRestaurantFragment);
//
//        }
//        if (mPostChildMapFragment != null && !mPostChildMapFragment.isHidden()) {
//            Log.d(Constants.TAG, "  transToProfile mPostFragment isHidden");
//            fragmentTransaction.remove(mPostChildMapFragment);
//        }

        mPostChildMapPresenter = new PostChildMapPresenter(mPostChildMapFragment);

        if (!mPostChildMapFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, mPostChildMapFragment, POST_CHILD_MAP);
        } else {
            fragmentTransaction.show(mPostChildMapFragment);
        }
//        fragmentTransaction.replace(R.id.fragment_container, mPostChildMapFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        mFoodieView.hideMapView();
        mFoodieView.showPostChildMapUi();
    }

    @Override
    public void transToPostArticle(String addressLine, LatLng latLng) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }


//        if (!mPostFragment.isAdded()) {
//            fragmentTransaction.add(R.id.fragment_container, mPostFragment, POST);
//        } else {
//            fragmentTransaction.show(mPostFragment);
//        }
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        mFoodieView.showPostArticleUi();

//        mPostPresenter = new PostPresenter(mPostFragment);
//        if (mPostPresenter == null){
            mPostPresenter = new PostPresenter(mPostFragment);

//        }
            mPostPresenter.setAddress(addressLine, latLng);

//        fragmentTransaction.hide(mPostChildMapFragment);
        fragmentTransaction.show(mPostFragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFoodieView.showPostArticleUi();
    }

    @Override
    public void getPostRestaurantPictures(ArrayList<String> stringArrayListExtra) {
        mPostPresenter.getPictures(stringArrayListExtra);
    }

    @Override
    public void transToPersonalArticle(Article article) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.emerge, R.anim.emerge);

        mPersonalArticleFragment = PersonalArticleFragment.newInstance();
        mPersonalArticlePresenter = new PersonalArticlePresenter(mPersonalArticleFragment, article);

//        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
//            fragmentTransaction.hide(mRestaurantFragment);
        fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.addToBackStack(RESTAURANT);
//        }
//        fragmentTransaction.hide(mRestaurantFragment);
//        fragmentTransaction.addToBackStack(RESTAURANT);

//        if(!mPersonalArticleFragment.isAdded()){
        fragmentTransaction.add(R.id.fragment_container, mPersonalArticleFragment, PERSONAL_ARTICLE);
        fragmentTransaction.commit();
//        }

    }

    @Override
    public void checkFragmentStatus() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPersonalArticleFragment != null && !mPersonalArticleFragment.isHidden()) {
            fragmentTransaction.hide(mPersonalArticleFragment);
            fragmentTransaction.commit();
            mFoodieView.setTabLayoutVisibility(false);
        } else {
            mFoodieView.setTabLayoutVisibility(true);
        }
    }


}
