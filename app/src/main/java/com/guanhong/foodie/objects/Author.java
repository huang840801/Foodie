package com.guanhong.foodie.objects;

public class Author {

    private String mId;
    private String mImage;
    private String mName;

    public Author() {
        mId = "";
        mImage = "";
        mName = "";
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
