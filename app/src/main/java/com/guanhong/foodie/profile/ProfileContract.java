package com.guanhong.foodie.profile;

import android.net.Uri;

import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.User;

import java.util.ArrayList;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

//        void showUserPicture(Bitmap bitmap);

        void showUserData(User user);

        void showMyArticles(ArrayList<Article> articleArrayList);

        void showUserNewPicture(Uri userNewPictureUri);

    }

    interface Presenter extends BasePresenter {

        void getMyArticleData();

        void updateUserImageToFireBaseStorage(ArrayList<String> pictures);


        void getUserData();
    }


}
