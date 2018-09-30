package com.guanhong.foodie.objects;

import java.util.ArrayList;

public class Article {
    private Author mAuthor;
    private String mRestaurantName;
    private String mLocation;
    private ArrayList<Menu> mMenus;
    private ArrayList<String> mPictures;
    private String mContent;
    private int mStarCount;

    public Article() {
        mAuthor = new Author();
        mRestaurantName = "";
        mLocation = "";
        mMenus = new ArrayList<>();
        mPictures = new ArrayList<>();
        mContent = "";
        mStarCount = 0;
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
}
