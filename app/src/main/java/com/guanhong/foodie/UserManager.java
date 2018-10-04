package com.guanhong.foodie;

import android.util.Log;

import com.guanhong.foodie.objects.User;

public class UserManager {

    private static UserManager userManager ;

    private String mUserId;
    private String mUserName;
    private String mUserImage;
    private String mUserEmail;

    public static UserManager getInstance() {
        if (userManager == null) {

            synchronized (UserManager.class) {

                if (userManager == null) {
                    userManager = new UserManager();
                }
            }
        }
        return userManager;    }

//    private UserManager() {
//    }

    public void setUserData(User user) {
        Log.d("UserManager", user.getId());
        Log.d("UserManager", user.getName());
        Log.d("UserManager", user.getEmail());
        mUserId = user.getId();
        mUserName = user.getName();
        mUserImage = user.getImage();
        mUserEmail = user.getEmail();
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserImage() {
        return mUserImage;
    }

    public String getUserEmail() {
        return mUserEmail;
    }
}
