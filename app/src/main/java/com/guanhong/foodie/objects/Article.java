package com.guanhong.foodie.objects;

import java.util.ArrayList;

public class Article {
    private String mId;
    private Author mAuthor;
    private String mTitle;
    private String mContent;
    private int mCreatedTime;
    private String mPlace;
    private ArrayList<String> mPictures;
    private int mInterests;
    private boolean mInterestedIn;

    public Article() {
        mId = "";
        mAuthor = new Author();
        mTitle = "";
        mContent = "";
        mCreatedTime = -1;
        mPlace = "";
        mPictures = new ArrayList<>();
        mInterests = -1;
        mInterestedIn = false;
    }
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(int createdTime) {
        mCreatedTime = createdTime;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public ArrayList<String> getPictures() {
        return mPictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        mPictures = pictures;
    }

    public int getInterests() {
        return mInterests;
    }

    public void setInterests(int interests) {
        mInterests = interests;
    }

    public boolean isInterestedIn() {
        return mInterestedIn;
    }

    public void setInterestedIn(boolean interestedIn) {
        mInterestedIn = interestedIn;
    }

}
