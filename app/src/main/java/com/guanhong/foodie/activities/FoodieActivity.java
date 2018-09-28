package com.guanhong.foodie.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guanhong.foodie.FoodieContract;
import com.guanhong.foodie.FoodiePresenter;
import com.guanhong.foodie.ViewPagerAdapter;
import com.guanhong.foodie.R;
import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.liked.LikedPresenter;
import com.guanhong.foodie.lotto.LottoFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.post.PostFragment;
import com.guanhong.foodie.post.PostPresenter;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class FoodieActivity extends BaseActivity implements FoodieContract.View, TabLayout.OnTabSelectedListener {

    private FoodieContract.Presenter mPresenter;

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private LottoFragment mLottoFragment;
    private LikedFragment mLikedFragment;
    private RestaurantFragment mRestaurantFragment;
    private PostFragment mPostFragment;

    private MapPresenter mMapPresenter;
    private LikedPresenter mLikedPresenter;
    private RestaurantPresenter mRestaurantPresenter;
    private PostPresenter mPostPresenter;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    //TabLayout标签
    private String[] mTitles;
    private List<Fragment> mFragmentList = new ArrayList<>();


    private Restaurant mRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        saveUserData();
    }

    private void saveUserData() {
        SharedPreferences userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String name = userData.getString("userName", "");
        String email = userData.getString("userEmail", "");
        String uid = userData.getString("userUid", "");


        Log.d(Constants.TAG, " userName : " + name);
        Log.d(Constants.TAG, " userEmail : " + email);
        Log.d(Constants.TAG, " userUid : " + uid);

        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = userDatabase.getReference("user");

        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setId(uid);

        myRef.child(uid).setValue(user1);



    }

    private void init() {

        setContentView(R.layout.activity_main);


        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mTitles = new String[]{
                getResources().getString(R.string.map),
                getResources().getString(R.string.profile),
                getResources().getString(R.string.search),
                getResources().getString(R.string.lottery),
                getResources().getString(R.string.like),
        };

        //设置TabLayout标签的显示方式
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //循环注入标签
        for (String tab : mTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }


        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
            mMapPresenter = new MapPresenter(mMapFragment, this);
        }
        if (mProfileFragment == null) {
            mProfileFragment = ProfileFragment.newInstance();
        }
        if (mSearchFragment == null) {
            mSearchFragment = SearchFragment.newInstance();
        }
        if (mLottoFragment == null) {
            mLottoFragment = LottoFragment.newInstance();
        }
        if (mLikedFragment == null) {
            mLikedFragment = LikedFragment.newInstance();
            mLikedPresenter = new LikedPresenter(mLikedFragment);

        }
//        if (mRestaurantFragment == null) {
//            mRestaurantFragment = RestaurantFragment.newInstance();
//        }

        mFragmentList.add(mMapFragment);
        mFragmentList.add(mProfileFragment);
        mFragmentList.add(mSearchFragment);
        mFragmentList.add(mLottoFragment);
        mFragmentList.add(mLikedFragment);
//        mFragmentList.add(new MapFragment());
//        mFragmentList.add(new ProfileFragment());
//        mFragmentList.add(new SearchFragment());
//        mFragmentList.add(new LottoFragment());
//        mFragmentList.add(new LikedFragment());
//        mFragmentList.add(mRestaurantFragment);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragmentList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mPresenter = new FoodiePresenter(this, mViewPager);
        mPresenter.start();
        //设置TabLayout点击事件
        mTabLayout.setOnTabSelectedListener(this);
    }

    public void setTabLayoutVisibility(boolean isVisible) {
        if (mTabLayout != null) {
            mTabLayout.setVisibility(isVisible ? (View.VISIBLE) : (View.GONE));
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(Constants.TAG, "onBackPressed: ");

//        if (mTabLayout == null) {
//            Log.d(Constants.TAG, "onBackPressed: ");
//            mTabLayout.setVisibility(View.VISIBLE);
//        } else {
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);


        super.onBackPressed();
//        }
    }

    @Override
    public void showMapUi() {

    }

    @Override
    public void showLikedUi() {

    }

    @Override
    public void showLotteryUi() {

    }

    @Override
    public void showProfileUi() {

    }

    @Override
    public void showSearchUi() {
//        Log.d(Constants.TAG, "  hello   transToSearch");

    }

    @Override
    public void showRestaurantUi(Restaurant restaurant) {
        Log.d(Constants.TAG, "  hello   transToRestaurant");
        mViewPager.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mRestaurantFragment == null) {
            mRestaurantFragment = RestaurantFragment.newInstance();
        }

        mRestaurantPresenter = new RestaurantPresenter(mRestaurantFragment, restaurant);

        fragmentTransaction.replace(R.id.fragment_container, mRestaurantFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void showPostArticleUi() {
        mViewPager.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mPostFragment == null) {
            mPostFragment = PostFragment.newInstance();
        }

        mPostPresenter = new PostPresenter(mPostFragment);

        fragmentTransaction.replace(R.id.fragment_container, mPostFragment, "");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void setPresenter(FoodieContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        mViewPager.setCurrentItem(tab.getPosition());
        Log.d(Constants.TAG, "  tab = " + tab.getPosition());

        switch (tab.getPosition()) {
            case 0:

                mPresenter.transToMap();
                break;
            case 1:

                mPresenter.transToProfile();
                break;
            case 2:

                mPresenter.transToSearch();
                break;
            case 3:

                mPresenter.transToLotto();
                break;
            case 4:

                mPresenter.transToLiked();
                break;

        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void transToRestaurant(Restaurant restaurant) {
//        Log.d("restaurant ", " FoodieActivity : " + restaurant);
        mPresenter.tranToRestaurant(restaurant);
    }

    public void transToPostArticle(){
        mPresenter.transToPostArticle();
    }
    public void pickPicture() {
        Intent picker = new Intent(Intent.ACTION_GET_CONTENT);
        picker.setType("image/*");
        picker.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        Intent destIntent = Intent.createChooser(picker, null);
        startActivityForResult(destIntent, Constants.PICKER);
    }
}
