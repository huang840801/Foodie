package com.guanhong.foodie.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.guanhong.foodie.FoodieContract;
import com.guanhong.foodie.FoodiePresenter;
import com.guanhong.foodie.MyFragmentPagerAdapter;
import com.guanhong.foodie.R;
import com.guanhong.foodie.liked.LikedFragment;
import com.guanhong.foodie.lottery.LotteryFragment;
import com.guanhong.foodie.map.MapFragment;
import com.guanhong.foodie.profile.ProfileFragment;
import com.guanhong.foodie.search.SearchFragment;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;


public class FoodieActivity extends BaseActivity implements FoodieContract.View, View.OnClickListener {

    private FoodieContract.Presenter mPresenter;

    private ViewPager mViewPager;

    private Button mButtonMap;
    private Button mButtonProfile;
    private Button mButtonSearch;
    private Button mButtonLottery;
    private Button mButtonLike;
    //作为指示标签的按钮
    private ImageView cursor;
    //标志指示标签的横坐标
    float cursorX = 0;
    //所有按钮的宽度的数组
    private int[] widthArgs;
    //所有标题按钮的数组
    private Button[] btnArgs;

    private ArrayList<Fragment> mFragmentArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) this.findViewById(R.id.myviewpager);

        mButtonMap = (Button) this.findViewById(R.id.btn_map);
        mButtonProfile = (Button) this.findViewById(R.id.btn_profile);
        mButtonSearch = (Button) this.findViewById(R.id.btn_search);
        mButtonLottery = (Button) this.findViewById(R.id.btn_lottery);
        mButtonLike = (Button) this.findViewById(R.id.btn_like);
        //初始化按钮数组
        btnArgs = new Button[]{mButtonMap, mButtonProfile, mButtonSearch, mButtonLottery, mButtonLike};

        cursor = (ImageView) this.findViewById(R.id.cursor_btn);
        cursor.setBackgroundColor(Color.RED);

        mButtonMap.setOnClickListener(this);
        mButtonProfile.setOnClickListener(this);
        mButtonSearch.setOnClickListener(this);
        mButtonLottery.setOnClickListener(this);
        mButtonLike.setOnClickListener(this);


        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new MapFragment());
        mFragmentArrayList.add(new ProfileFragment());
        mFragmentArrayList.add(new SearchFragment());
        mFragmentArrayList.add(new LotteryFragment());
        mFragmentArrayList.add(new LikedFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentArrayList);

        setButtonColor();
//        cursorAnim(0);
        mPresenter = new FoodiePresenter(this, mViewPager, adapter);
        mPresenter.start();

    }

    @Override
    public void setButtonColor() {

        Log.d(Constants.TAG, "  setButtonColor");

        //先重置所有按钮颜色
        resetButtonColor();
        //再将第一个按钮字体设置为红色，表示默认选中第一个
        mButtonMap.setTextColor(Color.RED);
//        setCursor(0);
    }

    @Override
    public void setCursor(int i) {
        Log.d(Constants.TAG, "  setCursorColor");

        if (widthArgs == null) {

            widthArgs = new int[]{
                    mButtonMap.getWidth(),
                    mButtonProfile.getWidth(),
                    mButtonSearch.getWidth(),
                    mButtonLottery.getWidth(),
                    mButtonLike.getWidth()};
        }

        resetButtonColor();
        btnArgs[i].setTextColor(Color.RED);
        cursorAnim(i);
    }

    private void cursorAnim(int curItem) {
        cursorX = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        lp.width = widthArgs[curItem] - btnArgs[0].getPaddingLeft() * 2;
        cursor.setLayoutParams(lp);

        for (int i = 0; i < curItem; i++) {
            cursorX = cursorX + btnArgs[i].getWidth();
        }
        cursor.setX(cursorX + btnArgs[curItem].getPaddingLeft());
    }


    private void resetButtonColor() {
        mButtonMap.setBackgroundColor(Color.parseColor("#DCDCDC"));
        mButtonProfile.setBackgroundColor(Color.parseColor("#DCDCDC"));
        mButtonSearch.setBackgroundColor(Color.parseColor("#DCDCDC"));
        mButtonLottery.setBackgroundColor(Color.parseColor("#DCDCDC"));
        mButtonLike.setBackgroundColor(Color.parseColor("#DCDCDC"));
        mButtonMap.setTextColor(Color.BLACK);
        mButtonProfile.setTextColor(Color.BLACK);
        mButtonSearch.setTextColor(Color.BLACK);
        mButtonLottery.setTextColor(Color.BLACK);
        mButtonLike.setTextColor(Color.BLACK);

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

    }


    @Override
    public void setPresenter(FoodieContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_map:
                mPresenter.transToMap();
//                myviewpager.setCurrentItem(0);
                break;
            case R.id.btn_profile:
                mPresenter.transToProfile();

//                myviewpager.setCurrentItem(1);
                break;
            case R.id.btn_search:
                mPresenter.transToSearch();

//                myviewpager.setCurrentItem(2);
                break;
            case R.id.btn_lottery:
                mPresenter.transToLottery();

//                myviewpager.setCurrentItem(3);
                break;
            case R.id.btn_like:
                mPresenter.transToLiked();

//                myviewpager.setCurrentItem(4);
                break;

            default:
                break;
        }
    }


}
