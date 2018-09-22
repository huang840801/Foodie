package com.guanhong.foodie;

import android.app.Application;
import android.content.Context;

public class Foodie extends Application {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
