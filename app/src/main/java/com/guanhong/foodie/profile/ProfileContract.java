package com.guanhong.foodie.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.User;

import java.util.ArrayList;

public interface ProfileContract {

    interface View extends BaseView<Presenter>{

        void showUserPicture(Bitmap bitmap);

        void showUserData(User user);

        void setArticleList(ArrayList<Article> articleArrayList);
    }

    interface Presenter extends BasePresenter{



        void updateUserImageToFireBase(String url);


        void getUserImage(Context context);
    }


}
