package com.guanhong.foodie.mainActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.BaseActivity;
import com.guanhong.foodie.activities.MapActivity;
import com.guanhong.foodie.util.UserManager;
import com.guanhong.foodie.adapters.ViewPagerAdapter;
import com.guanhong.foodie.like.LikeFragment;
import com.guanhong.foodie.like.LikePresenter;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.map.MapPresenter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.profile.ProfilePresenter;
import com.guanhong.foodie.recommend.RecommendFragment;
import com.guanhong.foodie.recommend.RecommendPresenter;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.search.SearchPresenter;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class FoodieActivity extends BaseActivity implements
        FoodieContract.View,
        TabLayout.OnTabSelectedListener {

    private FoodieContract.Presenter mPresenter;
    private MapFragment mMapFragment;
    private ProfileFragment mProfileFragment;
    private SearchFragment mSearchFragment;
    private RecommendFragment mRecommendFragment;
    private LikeFragment mLikeFragment;

    private ProfilePresenter mProfilePresenter;
    private TabLayout mTabLayout;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ConstraintLayout mLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        saveUserData();

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String address = intent.getExtras().getString(Constants.ADDRESS);
            String lat = intent.getExtras().getString(Constants.LAT);
            String lng = intent.getExtras().getString(Constants.LNG);
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

            transToPostArticle(address, latLng);
        }
    }

    @Override
    public void setPresenter(FoodieContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void pickMultiplePictures() {
        ArrayList<String> picturesList = new ArrayList<>();

        PhotoPickerIntent intent = new PhotoPickerIntent(FoodieActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setType(Constants.IMAGE_TYPE);
        intent.setMaxTotal(10);
        intent.setSelectedPaths(picturesList);

        startActivityForResult(intent, Constants.MULTIPLE_PICKER);
    }

    @Override
    public void transToPostChildMap() {

        Intent intent = new Intent(FoodieActivity.this, MapActivity.class);
        startActivityForResult(intent, Constants.CHILD_MAP_REQUEST_CODE);
    }

    @Override
    public void pickSinglePicture() {

        ArrayList<String> picturesList = new ArrayList<>();
        PhotoPickerIntent intent = new PhotoPickerIntent(FoodieActivity.this);

        intent.setSelectModel(SelectModel.MULTI);
        intent.setType(Constants.IMAGE_TYPE);
        intent.setMaxTotal(1);
        intent.setSelectedPaths(picturesList);

        startActivityForResult(intent, Constants.SINGLE_PICKER);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.map_selected);
                }
                mPresenter.transToMap();
                break;
            case 1:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.search_selected);
                }
                mPresenter.transToSearch();
                break;
            case 2:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.heart_selected);
                }
                mPresenter.transToLike();

                break;
            case 3:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.recommend_selected);
                }
                mPresenter.transToRecommend();

                break;
            case 4:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.portrait_selected);
                }
                mPresenter.transToProfile();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            default:
            case 0:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.map_normal);
                }
                break;
            case 1:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.search_normal);
                }
                break;
            case 2:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.heart_normal);
                }
                break;
            case 3:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.recommend_normal);
                }
                break;
            case 4:
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.imageView_custom_tab).setBackgroundResource(R.drawable.portrait_normal);
                }
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void transToPostArticle(String addressLine, LatLng latLng) {
        mPresenter.transToPostArticle(addressLine, latLng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SINGLE_PICKER) {

                if (data != null) {
                    ArrayList<String> pictures = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    mProfilePresenter.updateUserImageToFireBaseStorage(pictures);
                }

            } else if (requestCode == Constants.MULTIPLE_PICKER) {

                if (data != null) {
                    ArrayList<String> pictures = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    mPresenter.getPostRestaurantPictures(pictures);
                }
            } else if (requestCode == Constants.CHILD_MAP_REQUEST_CODE) {

                LatLng latLng = null;
                if (data != null) {
                    latLng = new LatLng(
                            Double.parseDouble(
                                    data.getExtras().getString("lat")),
                            Double.parseDouble(
                                    data.getExtras().getString("lng")));
                }

                if (data != null) {
                    mPresenter.transToPostArticle(
                            data.getExtras().getString(Constants.ADDRESS),
                            latLng);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onLowMemory();
    }

    private void saveUserData() {
        SharedPreferences userData = this.getSharedPreferences(
                Constants.USER_DATA,
                Context.MODE_PRIVATE);

        final String userId = userData.getString(Constants.USER_ID, "");

        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        Query query = userDatabase.getReference(Constants.USER);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (String.valueOf(snapshot.getKey()).equals(userId)) {

                        User user = new User();
                        user.setEmail((String) snapshot.child(Constants.EMAIL).getValue());
                        user.setId((String) snapshot.child(Constants.ID).getValue());
                        user.setImage((String) snapshot.child(Constants.IMAGE).getValue());
                        user.setName((String) snapshot.child(Constants.NAME).getValue());

                        UserManager.getInstance().setUserData(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void init() {
        requestReadAndWritePermissions();
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tab_layout);
        mLoadingLayout = findViewById(R.id.loading_layout);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ImageView loadingImageView = findViewById(R.id.loading_image);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.launch);
        loadingImageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        String[] titles = new String[]{
                getResources().getString(R.string.map),
                getResources().getString(R.string.search),
                getResources().getString(R.string.like),
                getResources().getString(R.string.lottery),
                getResources().getString(R.string.profile),
        };

        mPresenter = new FoodiePresenter(this, viewPager, getSupportFragmentManager());
        mPresenter.start();
        setTabLayout();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                titles,
                mFragmentList,
                this);
        viewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(viewPagerAdapter.getTabView(i));
        }
        mTabLayout.setOnTabSelectedListener(this);
    }

    private void setTabLayout() {

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        if (mMapFragment == null) {

            mMapFragment = MapFragment.newInstance();
            MapPresenter mapPresenter = new MapPresenter(mMapFragment);
            mapPresenter.setMainPresenter(mPresenter);
        }
        if (mProfileFragment == null) {

            mProfileFragment = ProfileFragment.newInstance();
            mProfilePresenter = new ProfilePresenter(mProfileFragment, this);
            mProfilePresenter.setMainPresenter(mPresenter);
        }
        if (mSearchFragment == null) {

            mSearchFragment = SearchFragment.newInstance();
            SearchPresenter searchPresenter = new SearchPresenter(mSearchFragment);
            searchPresenter.setMainPresenter(mPresenter);
        }
        if (mRecommendFragment == null) {

            mRecommendFragment = RecommendFragment.newInstance();
            RecommendPresenter recommendPresenter = new RecommendPresenter(mRecommendFragment);
            recommendPresenter.setPresenter(mPresenter);
        }
        if (mLikeFragment == null) {
            mLikeFragment = LikeFragment.newInstance();
            LikePresenter likePresenter = new LikePresenter(mLikeFragment);
            likePresenter.setMainPresenter(mPresenter);
        }

        mFragmentList.add(mMapFragment);
        mFragmentList.add(mSearchFragment);
        mFragmentList.add(mLikeFragment);
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(mProfileFragment);
    }

    private void requestReadAndWritePermissions() {

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
        return (ContextCompat.checkSelfPermission(
                getBaseContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(
                getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public void setTabLayoutVisibility(boolean isVisible) {
        mTabLayout.setVisibility(isVisible ? (View.VISIBLE) : (View.GONE));
    }

    public void transToMap() {
        mPresenter.transToMap();
    }

    public void checkRestaurantExists() {
        mPresenter.checkRestaurantExists();
    }

    public void transToRestaurant(Restaurant restaurant) {
        mPresenter.transToRestaurant(restaurant);
    }
}
