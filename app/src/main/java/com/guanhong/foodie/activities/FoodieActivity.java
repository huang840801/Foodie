package com.guanhong.foodie.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.guanhong.foodie.FoodieContract;
import com.guanhong.foodie.FoodiePresenter;
import com.guanhong.foodie.MyViewPagerAdapter;
import com.guanhong.foodie.R;
import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.lottery.LotteryFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class FoodieActivity extends BaseActivity implements FoodieContract.View, TabLayout.OnTabSelectedListener {

    private FoodieContract.Presenter mPresenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyViewPagerAdapter viewPagerAdapter;
    //TabLayout标签
    private String[] titles;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        titles = new String[]{
                getResources().getString(R.string.map),
                getResources().getString(R.string.profile),
                getResources().getString(R.string.search),
                getResources().getString(R.string.lottery),
                getResources().getString(R.string.like),
        };

        //设置TabLayout标签的显示方式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String tab : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }


        fragments.add(new MapFragment());
        fragments.add(new ProfileFragment());
        fragments.add(new SearchFragment());
        fragments.add(new LotteryFragment());
        fragments.add(new LikedFragment());
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mPresenter = new FoodiePresenter(this, viewPager);
        mPresenter.start();
        //设置TabLayout点击事件
        tabLayout.setOnTabSelectedListener(this);
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
        Log.d(Constants.TAG, "  hello   transToSearch");

    }


    @Override
    public void setPresenter(FoodieContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        viewPager.setCurrentItem(tab.getPosition());
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
}
