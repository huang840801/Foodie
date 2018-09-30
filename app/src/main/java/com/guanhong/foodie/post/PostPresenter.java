package com.guanhong.foodie.post;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostPresenter implements PostContract.Presenter{

    private PostContract.View mPostView;

    private Context mContext;

    public PostPresenter(PostContract.View postView) {

        mPostView = checkNotNull(postView, "postView cannot be null");
        mPostView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void showTabLayout() {
        mPostView.setTabLayoutVisibility(true);

    }

    @Override
    public void hideTabLayout() {
        mPostView.setTabLayoutVisibility(false);

    }

    @Override
    public void postArticle(Article article) {

        Log.d(Constants.TAG, " postArticle  getAuthor getId = " + article.getAuthor().getId());
        Log.d(Constants.TAG, " postArticle  getAuthor getName = " + article.getAuthor().getName());
        Log.d(Constants.TAG, " postArticle  getRestaurantName = " + article.getRestaurantName());
        Log.d(Constants.TAG, " postArticle  getLocation = " + article.getLocation());
        Log.d(Constants.TAG, " postArticle  getMenus = " + article.getMenus().get(0).getDishName());
        Log.d(Constants.TAG, " postArticle  getMenus = " + article.getMenus().get(0).getDishPrice());
        Log.d(Constants.TAG, " postArticle mStarCount = " + article.getContent());
        Log.d(Constants.TAG, " postArticle getStarCount = " + article.getStarCount());
        Log.d(Constants.TAG, " postArticle latitude = " + article.getLatLng().latitude);
        Log.d(Constants.TAG, " postArticle longitude = " + article.getLatLng().longitude);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myArticleDataBase = firebaseDatabase.getReference("restaurant");

        myArticleDataBase.child(article.getLocation()).setValue(article);

    }

    public void setAddress(String addressLine) {
        mPostView.showAddress(addressLine);
    }

    public void getPictures(ArrayList<String> stringArrayListExtra) {
        mPostView.showPictures(stringArrayListExtra);
    }
}
