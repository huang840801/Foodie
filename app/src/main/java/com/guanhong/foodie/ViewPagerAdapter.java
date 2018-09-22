package com.guanhong.foodie;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] mTitles;
    private FragmentManager mFragmentManager;

    public ViewPagerAdapter(FragmentManager fragmentManager, String[] titles, List<Fragment> fragments) {
        super(fragmentManager);
        this.mTitles = titles;
        this.mFragmentList = fragments;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragmentList.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        this.mFragmentManager.beginTransaction().show(fragment).commit();

        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        Fragment fragment = mFragmentList.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
//        super.destroyItem(container, position, object);
    }
}