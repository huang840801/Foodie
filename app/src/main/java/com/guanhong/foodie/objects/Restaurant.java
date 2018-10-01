package com.guanhong.foodie.objects;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Restaurant {

    private ArrayList<String> mRestaurantPictures;
    private ArrayList<Article> mArticleArrayList;
    private int mStarCount;
    private String mRestaurantName;
    private String mRestaurantLocation;
    private String mLat_Lng;


    public Restaurant() {

        mRestaurantPictures = new ArrayList<>();
        mArticleArrayList = new ArrayList<>();
        mStarCount = -1;
        mRestaurantName = "";
        mRestaurantLocation = "";
        mLat_Lng = "";


    }

    public ArrayList<String> getRestaurantPictures() {
        return mRestaurantPictures;
    }

    public void setRestaurantPictures(ArrayList<String> restaurantPictures) {
        mRestaurantPictures = restaurantPictures;
    }

    public int getStarCount() {
        return mStarCount;
    }

    public void setStarCount(int starCount) {
        mStarCount = starCount;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        mRestaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return mRestaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        mRestaurantLocation = restaurantLocation;
    }

    public ArrayList<Article> getArticleArrayList() {
        return mArticleArrayList;
    }

    public void setArticleArrayList(ArrayList<Article> articleArrayList) {
        mArticleArrayList = articleArrayList;
    }

    public String getLat_Lng() {
        return mLat_Lng;
    }

    public void setLat_Lng(String lat_Lng) {
        mLat_Lng = lat_Lng;
    }
}