package com.guanhong.foodie;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

public class Foodie extends Application {
    private static Context mContext;
    private static LruCache mLruCache;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initLruCache();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static LruCache getLruCache() {
        return mLruCache;
    }

    private void initLruCache() {

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 2;

        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }
}
