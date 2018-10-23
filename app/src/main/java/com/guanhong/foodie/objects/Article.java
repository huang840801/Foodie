package com.guanhong.foodie.objects;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Article {
    private Author mAuthor;
    private String mRestaurantName;
    private String mLocation;
    private ArrayList<Menu> mMenus;
    private ArrayList<String> mPictures;
    private String mContent;
    private int mStarCount;
    private LatLng mLatLng;
    private String lat_lng;
    private String mCreatedTime;


    public Article() {
        mAuthor = new Author();
        mRestaurantName = "";
        mLocation = "";
        mMenus = new ArrayList<>();
        mPictures = new ArrayList<>();
        mContent = "";
        mStarCount = 0;
        mLatLng = new LatLng(0, 0);
        lat_lng = "";
        mCreatedTime = "";
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        mRestaurantName = restaurantName;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public ArrayList<Menu> getMenus() {
        return mMenus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        mMenus = menus;
    }

    public ArrayList<String> getPictures() {
        return mPictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        mPictures = pictures;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getStarCount() {
        return mStarCount;
    }

    public void setStarCount(int starCount) {
        mStarCount = starCount;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public String getLat_lng() {
        return lat_lng;
    }

    public void setLat_lng(String lat_lng) {
        this.lat_lng = lat_lng;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        mCreatedTime = createdTime;
    }
}
