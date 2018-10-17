package com.guanhong.foodie.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.foamtrace.photopicker.ImageConfig;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.FoodieContract;
import com.guanhong.foodie.FoodiePresenter;

import com.guanhong.foodie.MyService;
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.ViewPagerAdapter;
import com.guanhong.foodie.R;
import com.guanhong.foodie.like.LikeFragment;
import com.guanhong.foodie.like.LikePresenter;
import com.guanhong.foodie.recommend.RecommendFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.post.PostFragment;
import com.guanhong.foodie.post.PostPresenter;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.profile.ProfilePresenter;
import com.guanhong.foodie.recommend.RecommendPresenter;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.restaurant.RestaurantPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.search.SearchPresenter;
import com.guanhong.foodie.util.Constants;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class FoodieActivity extends BaseActivity implements FoodieContract.View, TabLayout.OnTabSelectedListener {

    private FoodieContract.Presenter mPresenter;
    private static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 102;

    private Context mContext;

    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private RecommendFragment mRecommendFragment;
    private LikeFragment mLikeFragment;
    private RestaurantFragment mRestaurantFragment;
    private PostFragment mPostFragment;

    private MapPresenter mMapPresenter;
    private ProfilePresenter mProfilePresenter;
    private LikePresenter mLikePresenter;
    private SearchPresenter mSearchPresenter;
    private RecommendPresenter mRecommendPresenter;
    private RestaurantPresenter mRestaurantPresenter;
    private PostPresenter mPostPresenter;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    //TabLayout标签
    private String[] mTitles;
    private List<Fragment> mFragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Logger.d("hiiii");
//        Log.d("UserManager ", "email = " + UserManager.getInstance().getUserEmail());
//        Log.d("UserManager ", "id = " + UserManager.getInstance().getUserId());
//        Log.d("UserManager ", "image = " + UserManager.getInstance().getUserImage());
//        Log.d("UserManager ", "name = " + UserManager.getInstance().getUserName());

//        String s = "s";
//        String empty = null;
//        if(!"".equals(s)){
//            Log.d(Constants.TAG, "helloooo");
//        }
//        if (!"s".equals(s)) {
//            Log.d(Constants.TAG, "helloooo sssss");
//        }
//        if(!"".equals(empty)){
//            Log.d(Constants.TAG, "helloooo empty");
//        }

        super.onCreate(savedInstanceState);

        init();
        saveUserData();
    }

    private void saveUserData() {
        SharedPreferences userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
//        String name = userData.getString("userName", "");
//        String email = userData.getString("userEmail", "");
        final String userId = userData.getString("userId", "");
//        String image = userData.getString("userImage", "");
//        Log.d(Constants.TAG, " userName : " + name);
//        Log.d(Constants.TAG, " userEmail : " + email);
        Log.d(Constants.TAG, " SharedPreferences userUid : " + userId);
//        Log.d(Constants.TAG, " userImage : " + image);

        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = userDatabase.getReference("user");
        Query query = myRef;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (String.valueOf(snapshot.getKey()).equals(userId)) {
                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot);
                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.getKey());
                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.child("email").getValue());
                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.child("id").getValue());
                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.child("image").getValue());
                        Log.d(Constants.TAG, " FoodieActivityDataSnapshot : " + snapshot.child("name").getValue());

                        User user = new User();
                        user.setEmail((String) snapshot.child("email").getValue());
                        user.setId((String) snapshot.child("id").getValue());
                        user.setImage((String) snapshot.child("image").getValue());
                        user.setName((String) snapshot.child("name").getValue());

                        UserManager.getInstance().setUserData(user);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setId(uid);
//        user.setImage(image);
//
//        UserManager userManager = UserManager.getInstance();
//
//        userManager.setUserData(user);
//
//
//        myRef.child(uid).setValue(user);


    }

    private void init() {

        mContext = this;


        requestReadAndWritePermissions();

        setContentView(R.layout.activity_main);

        ImageConfig config = new ImageConfig();
        config.minHeight = 400;
        config.minWidth = 400;
        config.mimeType = new String[]{"image/jpeg", "image/png"}; // 图片类型 image/gif ...
        config.minSize = 1024 * 1024; // 1Mb 图片大小


        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mTitles = new String[]{
                getResources().getString(R.string.map),
                getResources().getString(R.string.search),
                getResources().getString(R.string.like),
                getResources().getString(R.string.lottery),
                getResources().getString(R.string.profile),

        };

        setTabLayout();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragmentList, this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


//        mTabLayout.getTabAt(0).setCustomView(mViewPagerAdapter.getTabView(0));
//        mTabLayout.getTabAt(1).setCustomView(mViewPagerAdapter.getTabView(1));
//        mTabLayout.getTabAt(2).setCustomView(mViewPagerAdapter.getTabView(2));
//        mTabLayout.getTabAt(3).setCustomView(mViewPagerAdapter.getTabView(3));
//        mTabLayout.getTabAt(4).setCustomView(mViewPagerAdapter.getTabView(4));


        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                mTabLayout.getTabAt(i).setCustomView(mViewPagerAdapter.getTabView(i));

        }
//        onTabSelected(mTabLayout.getTabAt(0));

//        mTabLayout.getTabAt(0).getCustomView().setSelected(true);

        mPresenter = new FoodiePresenter(this, mViewPager, getSupportFragmentManager());
        mPresenter.start();

//        mTabLayout.getTabAt(0).select();
        //设置TabLayout点击事件
        mTabLayout.setOnTabSelectedListener(this);
//        mTabLayout.getTabAt(1).getCustomView().setSelected(true);
//        mViewPager.setCurrentItem(0);

    }


    private void setTabLayout() {
        //设置TabLayout标签的显示方式
//        mTabLayout.getTabAt(0).select();
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        //循环注入标签
//        for (String tab : mTitles) {
//            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
//
//        }

        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
            mMapPresenter = new MapPresenter(mMapFragment);
        }
        if (mProfileFragment == null) {
            mProfileFragment = ProfileFragment.newInstance();
            mProfilePresenter = new ProfilePresenter(mProfileFragment, mContext);

        }
        if (mSearchFragment == null) {
            mSearchFragment = SearchFragment.newInstance();
            mSearchPresenter = new SearchPresenter(mSearchFragment);

        }
        if (mRecommendFragment == null) {
            mRecommendFragment = RecommendFragment.newInstance();
            mRecommendPresenter = new RecommendPresenter(mRecommendFragment);
        }
        if (mLikeFragment == null) {
            mLikeFragment = LikeFragment.newInstance();
            mLikePresenter = new LikePresenter(mLikeFragment);

        }

        mFragmentList.add(mMapFragment);
        mFragmentList.add(mSearchFragment);
        mFragmentList.add(mLikeFragment);
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(mProfileFragment);
    }

    private void requestReadAndWritePermissions() {
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
                }, Constants.REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


    public void setTabLayoutVisibility(boolean isVisible) {
        mTabLayout.setVisibility(isVisible ? (View.VISIBLE) : (View.GONE));
    }

    @Override
    public void onBackPressed() {
        Log.d(Constants.TAG, "onBackPressed: ");

//        mPresenter.checkFragmentStatus();
//        mPresenter.checkPostMapExist();
//        mViewPager.setVisibility(View.VISIBLE);
//        mTabLayout.setVisibility(View.VISIBLE);

        super.onBackPressed();
    }

    @Override
    public void showMapUi() {
        setTabLayoutVisibility(true);

    }

    @Override
    public void showLikedUi() {
        setTabLayoutVisibility(true);

    }

    @Override
    public void showLotteryUi() {
        setTabLayoutVisibility(true);

    }

    @Override
    public void showProfileUi() {
        setTabLayoutVisibility(true);
    }

    @Override
    public void showSearchUi() {
//        Log.d(Constants.TAG, "  hello   transToSearch");
        setTabLayoutVisibility(true);

    }

    @Override
    public void showRestaurantUi() {
        Log.d(Constants.TAG, "  hello   transToRestaurant");
//        mViewPager.setVisibility(View.GONE);
        setTabLayoutVisibility(false);

    }

    @Override
    public void showPostArticleUi() {
//        mViewPager.setVisibility(View.GONE);

    }

    @Override
    public void showPostChildMapUi() {
//        mViewPager.setVisibility(View.GONE);

    }


    @Override
    public void setPresenter(FoodieContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d("getTabView ", " onTabSelected position = " + tab.getPosition());

        switch (tab.getPosition()) {
            case 0:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.map_selected);
                mPresenter.transToMap();
                break;
            case 1:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.search_selected);
                mPresenter.transToSearch();
                break;
            case 2:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.heart_selected);
                mPresenter.transToLike();

                break;
            case 3:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.recommend_selected);
                mPresenter.transToRecommend();
                break;
            case 4:

                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.portrait_selected);
                mPresenter.transToProfile();
                break;

        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        Log.d("getTabView ", " onTabUnselected position = " + tab.getPosition());


        switch (tab.getPosition()) {
            case 0:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.map_normal);
                break;
            case 1:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.search_normal);

                break;
            case 2:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.heart_normal);

                break;
            case 3:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.recommend_normal);
                break;
            case 4:
                tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.portrait_normal);

                break;

        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void transToRestaurant(Restaurant restaurant, ArrayList<Comment> comments) {
//        Log.d("restaurant ", " FoodieActivity : " + restaurant);
        mPresenter.tranToRestaurant(restaurant, comments);
    }

    public void transToPostArticle() {
        mPresenter.transToPostArticle();
    }

    public void transToPostChildMap() {
        mPresenter.transToPostChildMap();
    }

    public void transToPostArticle(String addressLine, LatLng latLng) {
        mPresenter.transToPostArticle(addressLine, latLng);
    }

    public void transToPersonalArticle(Article article) {
        mPresenter.transToPersonalArticle(article);
    }


    public void transToProfile() {
        mPresenter.transToProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == Constants.SINGLE_PICKER) {
//                Log.d("SINGLE_PICKER ", data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT) + "");
//                Uri uri = Uri.parse(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0));
//                Log.d("SINGLE_PICKER uri = ", String.valueOf(uri));

//                Uri uri = data.getData();
//                Log.d(" updateUserImage  ",  "onActivityResult  "+uri.toString());

                ArrayList<String> pictures = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);

                Log.d("updateUserImage ", " onActivityResult" + pictures);
                Log.d("updateUserImage ", " onActivityResult" + pictures.size());

                mProfilePresenter.updateUserImageToFireBaseStorage(pictures);
//                SharedPreferences userImage = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
//                userImage.edit()
//                        .putString("userImage", String.valueOf(uri))
//                        .commit();

            } else if (requestCode == Constants.MULTIPLE_PICKER) {
//                Log.d("MULTIPLE_PICKER ", data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT) + "");
//                Log.d("MULTIPLE_PICKER ", data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).size() + "");

                ArrayList<String> pictures = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);

                Log.d("MULTIPLE_PICKER ", "" + pictures);
                Log.d("MULTIPLE_PICKER ", "" + pictures.size());

                mPresenter.getPostRestaurantPictures(pictures);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onLowMemory();
    }

    public void pickSinglePicture() {


//        Intent intent = new Intent();
//        //開啟Pictures畫面Type設定為image
//        intent.setType("image/*");
//        //使用Intent.ACTION_GET_CONTENT這個Action
//        //會開啟選取圖檔視窗讓您選取手機內圖檔
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        //取得相片後返回本畫面
//        startActivityForResult(intent, Constants.SINGLE_PICKER);

        ArrayList<String> picturesList = new ArrayList<>();

        PhotoPickerIntent intent = new PhotoPickerIntent(FoodieActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setType("image/*");

//        intent.setShowCarema(true);
        intent.setMaxTotal(1);
        intent.setSelectedPaths(picturesList);

        startActivityForResult(intent, Constants.SINGLE_PICKER);


    }


    public void pickMultiplePictures() {

        ArrayList<String> picturesList = new ArrayList<>();


        PhotoPickerIntent intent = new PhotoPickerIntent(FoodieActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setType("image/*");

//        intent.setShowCarema(true);
        intent.setMaxTotal(10);
        intent.setSelectedPaths(picturesList);

        startActivityForResult(intent, Constants.MULTIPLE_PICKER);

    }


}
