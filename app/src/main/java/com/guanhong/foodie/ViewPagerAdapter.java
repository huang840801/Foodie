package com.guanhong.foodie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.activities.FoodieActivity;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] mTitles;
    private FragmentManager mFragmentManager;
    private Context mContext;
    private int[] ints = {R.drawable.map_normal, R.drawable.portrait_normal, R.drawable.search_normal, R.drawable.recommend_normal, R.drawable.heart_normal};

    public ViewPagerAdapter(FragmentManager fragmentManager, String[] titles, List<Fragment> fragments, Context context) {
        super(fragmentManager);
        this.mTitles = titles;
        this.mFragmentList = fragments;
        this.mFragmentManager = fragmentManager;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragmentList.get(arg0);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitles[position];
//    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.mFragmentManager.beginTransaction().show(fragment).commit();

        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        Fragment fragment = mFragmentList.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
//        super.destroyItem(container, position, object);
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tabview, null);
        ImageView imageView = v.findViewById(R.id.imageView_custom_tab);
//        TextView textView = v.findViewById(R.id.textView_custom_tab);
        imageView.setImageResource(ints[position]);
//        textView.setText(mTitles[position]);


        Log.d("getTabView ", "  position = " + position);
//        if(position==0){
//            Log.d("updateUserImage ", "  position = " + position);
//
//            imageView.setImageResource(R.drawable.map_selected);
//        }
        return v;
    }


}