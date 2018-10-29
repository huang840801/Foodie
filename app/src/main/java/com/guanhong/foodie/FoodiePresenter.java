package com.guanhong.foodie;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.like.LikeFragment;
import com.guanhong.foodie.like.LikePresenter;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.personal.PersonalFragment;
import com.guanhong.foodie.personal.PersonalPresenter;
import com.guanhong.foodie.post.PostFragment;
import com.guanhong.foodie.post.PostPresenter;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.profile.ProfilePresenter;
import com.guanhong.foodie.recommend.RecommendFragment;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;


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
    private PersonalFragment mPersonalFragment;


    private MapPresenter mMapPresenter;
    private ProfilePresenter mProfilePresenter;
    private RestaurantPresenter mRestaurantPresenter;
    private LikePresenter mLikePresenter;
    private PostPresenter mPostPresenter;
    private PersonalPresenter mPersonalPresenter;

    private ViewPager mViewPager;


    public FoodiePresenter(FoodieContract.View foodieView, ViewPager viewPager, FragmentManager supportFragmentManager) {
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
            Log.d(Constants.TAG, "   mPostFragment !isHidden");
            mFragmentManager.popBackStack();
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
            mFragmentManager.popBackStack();
        }

        mViewPager.setCurrentItem(0);
        mFoodieView.setTabLayoutVisibility(true);

    }

    @Override
    public void transToProfile() {

        Log.d(Constants.TAG, "  transToProfile");
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment != null && !mPostFragment.isHidden()) {
            Log.d(Constants.TAG, "   mPostFragment !isHidden");
            mFragmentManager.popBackStack();
        }
        if (mRestaurantFragment != null && !mRestaurantFragment.isHidden()) {
            Log.d(Constants.TAG, "  transToMap mRestaurantFragment isHidden");
            mFragmentManager.popBackStack();
        }
        fragmentTransaction.commit();

        mViewPager.setCurrentItem(4);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToSearch() {
        Log.d(Constants.TAG, "  transToSearch");

        mViewPager.setCurrentItem(1);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToRecommend() {
        Log.d(Constants.TAG, "  transToRecommend");
        mViewPager.setCurrentItem(3);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToLike() {
        Log.d(Constants.TAG, "  transToLiked");
        mViewPager.setCurrentItem(2);
        mFoodieView.setTabLayoutVisibility(true);
    }

    @Override
    public void transToRestaurant(Restaurant restaurant, ArrayList<Comment> comments) {
        Log.d("restaurant ", " FoodiePresenter : " + restaurant);
        Log.d("myCommentsBug ", "  FoodiePresenter  comments.size = " + comments.size());
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
        fragmentTransaction.addToBackStack(null);

        mFoodieView.setTabLayoutVisibility(false);
        fragmentTransaction.commit();

    }

    @Override
    public void transToRestaurant(Restaurant restaurant) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mRestaurantFragment == null) {
            mRestaurantFragment = RestaurantFragment.newInstance();
        }

        mRestaurantPresenter = new RestaurantPresenter(mRestaurantFragment, restaurant);

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
    public void transToPostArticle() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

        mPostPresenter = new PostPresenter(mPostFragment);

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
    public void transToPersonalArticle(Article article) {


        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_transition, R.anim.fragment_transition);

        mPersonalFragment = PersonalFragment.newInstance();
        mPersonalPresenter = new PersonalPresenter(mPersonalFragment, article);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, mPersonalFragment, PERSONAL_ARTICLE);
        fragmentTransaction.commit();
//        }
        mFoodieView.setTabLayoutVisibility(false);

    }

    @Override
    public void checkRestaurantExists() {

        Log.d("fragmentflow", "  check mRestaurantFragment = " + mRestaurantFragment);

        if (mFragmentManager.findFragmentByTag(RESTAURANT) != null) {
            Log.d("fragmentflow", "   mRestaurantFragment != null ");
            mFoodieView.setTabLayoutVisibility(false);
        } else {
            Log.d("fragmentflow", "   mRestaurantFragment == null ");
            mFoodieView.setTabLayoutVisibility(true);
        }
    }



}
