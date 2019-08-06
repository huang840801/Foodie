package com.guanhong.foodie.objects;

import java.util.ArrayList;

public class Restaurant {

    private ArrayList<String> mRestaurantPictures;
    private int mStarCount;
    private String mRestaurantName;
    private String mRestaurantLocation;
    private String mLatLng;

    public Restaurant() {

        mRestaurantPictures = new ArrayList<>();
        mStarCount = -1;
        mRestaurantName = "";
        mRestaurantLocation = "";
        mLatLng = "";
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

    public String getLat_Lng() {
        return mLatLng;
    }

    public void setLat_Lng(String latLng) {
        mLatLng = latLng;
    }
}
