package com.guanhong.foodie.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.guanhong.foodie.profile.ProfilePresenter;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class FoodieActivity extends BaseActivity implements FoodieContract.View, TabLayout.OnTabSelectedListener {

    private FoodieContract.Presenter mPresenter;
    private static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 102;

    private Context mContext;

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private LottoFragment mLottoFragment;
    private LikedFragment mLikedFragment;
    private RestaurantFragment mRestaurantFragment;
    private PostFragment mPostFragment;

    private MapPresenter mMapPresenter;
    private ProfilePresenter mProfilePresenter;
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
        String image = userData.getString("userImage", "");


//        int i =9/10;
//        int j =9%10;
//        Log.d(Constants.TAG, " iiiiiiii : " + i +"jjjj"+j);
        Log.d(Constants.TAG, " userName : " + name);
        Log.d(Constants.TAG, " userEmail : " + email);
        Log.d(Constants.TAG, " userUid : " + uid);
        Log.d(Constants.TAG, " userImage : " + image);

        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = userDatabase.getReference("user");

        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setId(uid);
        user1.setImage(image);

        myRef.child(uid).setValue(user1);


    }

    private void init() {

        setContentView(R.layout.activity_main);

        requestAppPermissions();

        mContext = this;

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mTitles = new String[]{
                getResources().getString(R.string.map),
                getResources().getString(R.string.profile),
                getResources().getString(R.string.search),
                getResources().getString(R.string.lottery),
                getResources().getString(R.string.like),
        };

        setTabLayout();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragmentList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mPresenter = new FoodiePresenter(this, mViewPager, getSupportFragmentManager(), mContext);
        mPresenter.start();
        //设置TabLayout点击事件
        mTabLayout.setOnTabSelectedListener(this);
    }

    private void setTabLayout() {
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
            mProfilePresenter = new ProfilePresenter(mProfileFragment, mContext);

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

        mFragmentList.add(mMapFragment);
        mFragmentList.add(mProfileFragment);
        mFragmentList.add(mSearchFragment);
        mFragmentList.add(mLottoFragment);
        mFragmentList.add(mLikedFragment);
    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
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
        mPresenter.checkPostMapExist();
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);


        super.onBackPressed();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//         if (requestCode == Constants.PICKER && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            Log.d("URI!!!!!!! ", "onActivityResult: " + uri);
//            mPresenter.passUri(uri);
//            mProfilePresenter.getNewPicture(uri);
//        }

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("hello uri???", uri.toString());

            mProfilePresenter.getPicture(uri);
            SharedPreferences userName = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
            userName.edit()
                    .putString("userImage", String.valueOf(uri))
                    .commit();

        }


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

    }

    @Override
    public void showPostArticleUi() {
        mViewPager.setVisibility(View.GONE);

    }

    @Override
    public void showPostChildMapUi() {
        mViewPager.setVisibility(View.GONE);

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

    public void transToPostArticle() {
        mPresenter.transToPostArticle();
    }

    public void transToPostChildMap() {
        mPresenter.transToPostChildMap();
    }



    public void pickPicture() {


        Intent intent = new Intent();
        //開啟Pictures畫面Type設定為image
        intent.setType("image/*");
        //使用Intent.ACTION_GET_CONTENT這個Action
        //會開啟選取圖檔視窗讓您選取手機內圖檔
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //取得相片後返回本畫面
        startActivityForResult(intent, 1);
    }

    public void transToPostArticle(String addressLine) {
        mPresenter.transToPostArticle(addressLine);
    } public void transToPostProfile() {
        mPresenter.transToProfile();
    }
}
