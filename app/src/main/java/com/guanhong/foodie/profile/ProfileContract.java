package com.guanhong.foodie.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;

public interface ProfileContract {

    interface View extends BaseView<Presenter>{

        void showMypicture(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter{


        void uploadToFirebase(String path);

        void updateUserImageToFireBase(String url);


        void getUserImage(Context context);
    }


}
