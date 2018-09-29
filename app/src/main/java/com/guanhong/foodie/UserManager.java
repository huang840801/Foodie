package com.guanhong.foodie;

public class UserManager {

    private String mUserId;
    private String mUserName;
    private String mUserImage;
    private String mUserEmail;

    public static UserManager getInstance() {
        return new UserManager();
    }

    private UserManager() {

    }
}
