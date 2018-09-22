package com.guanhong.foodie.objects;

public class User {
    private String mName;
    private String mEmail;
    private String mImage;
    private String mId;

    public User() {
        mName = "";
        mEmail = "";
        mImage = "";
        mId = "";
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
