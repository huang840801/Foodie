package com.guanhong.foodie.mainActivity;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.R;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.personal.PersonalFragment;
import com.guanhong.foodie.personal.PersonalPresenter;
import com.guanhong.foodie.post.PostFragment;
import com.guanhong.foodie.post.PostPresenter;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

public class FoodiePresenter implements FoodieContract.Presenter {

    private static final String RESTAURANT = "RESTAURANT";
    private static final String POST = "POST";
    private static final String PERSONAL_ARTICLE = "PERSONALARTICLE";

    private FoodieContract.View mFoodieView;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private ViewPager mViewPager;

    private RestaurantFragment mRestaurantFragment;
    private PostFragment mPostFragment;
    private PostPresenter mPostPresenter;


    FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager, FragmentManager supportFragmentManager) {
        mFoodieView = checkNotNull(foodieView, "foodieView cannot be null!");
        mFoodieView.setPresenter(this);

        mViewPager = viewPager;
        mFragmentManager = supportFragmentManager;
    }


    @Override
    public void start() {
        transToMap();
    }

    public void transToMap() {

        if (mPostFragment != null && !mPostFragment.isHidden()) {
            mFragmentManager.popBackStack();
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            mFragmentManager.popBackStack();
        }

        mViewPager.setCurrentItem(0);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToProfile() {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPostFragment != null && !mPostFragment.isHidden()) {
            mFragmentManager.popBackStack();
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            mFragmentManager.popBackStack();
        }

        fragmentTransaction.commit();

        mViewPager.setCurrentItem(4);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToSearch() {

        mViewPager.setCurrentItem(1);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToRecommend() {

        mViewPager.setCurrentItem(3);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToLike() {

        mViewPager.setCurrentItem(2);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToPostArticle() {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

        mPostPresenter = new PostPresenter(mPostFragment);
        mPostPresenter.setMainPresenter(this);


        if (!mPostFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, mPostFragment, POST);
        } else {
            fragmentTransaction.show(mPostFragment);
        }
        fragmentTransaction.addToBackStack(null);

        mFoodieView.setTabLayoutVisibility(false);

        fragmentTransaction.commit();
    }

    @Override
    public void transToPostArticle(String addressLine, LatLng latLng) {

        final FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

        mPostPresenter = new PostPresenter(mPostFragment);
        mPostPresenter.setAddress(addressLine, latLng);

        fragmentTransaction.show(mPostFragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void getPostRestaurantPictures(ArrayList<String> stringArrayListExtra) {
        mPostPresenter.getPictures(stringArrayListExtra);
    }

    @Override
    public void transToRestaurant(Restaurant restaurant) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mRestaurantFragment == null) {
            mRestaurantFragment = RestaurantFragment.newInstance();
        }

        RestaurantPresenter mRestaurantPresenter = new RestaurantPresenter(mRestaurantFragment, restaurant);
        mRestaurantPresenter.setMainPresenter(this);


        if (!mRestaurantFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, mRestaurantFragment, RESTAURANT);
        } else {
            fragmentTransaction.show(mRestaurantFragment);
        }
        fragmentTransaction.addToBackStack(null);

        mFoodieView.setTabLayoutVisibility(false);
        fragmentTransaction.commit();
    }

    @Override
    public void transToPersonalArticle(Article article) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.trans_to_personal, R.anim.trans_to_personal);

        PersonalFragment mPersonalFragment = PersonalFragment.newInstance();
        PersonalPresenter mPersonalPresenter = new PersonalPresenter(mPersonalFragment, article);
        mPersonalPresenter.setMainPresenter(this);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, mPersonalFragment, PERSONAL_ARTICLE);
        fragmentTransaction.commit();
        mFoodieView.setTabLayoutVisibility(false);

    }

    @Override
    public void checkRestaurantExists() {

        if (mFragmentManager.findFragmentByTag(RESTAURANT) != null) {
            mFoodieView.setTabLayoutVisibility(false);
        } else {
            mFoodieView.setTabLayoutVisibility(true);
        }
    }

    @Override
    public void pickMultiplePictures() {
        mFoodieView.pickMultiplePictures();
    }

    @Override
    public void transToPostChildMap() {
        mFoodieView.transToPostChildMap();
    }

    @Override
    public void pickSinglePicture() {
        mFoodieView.pickSinglePicture();
    }

    @Override
    public void setTabLayoutVisibility(boolean isTabLayoutVisibility) {
        mFoodieView.setTabLayoutVisibility(isTabLayoutVisibility);
    }
}
