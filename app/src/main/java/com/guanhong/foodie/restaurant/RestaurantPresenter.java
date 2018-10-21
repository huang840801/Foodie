package com.guanhong.foodie.restaurant;

import android.util.Log;

import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;


public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.View mRestaurantView;
    private Restaurant mRestaurant;
    private ArrayList<Comment> mComments;

    @Override
    public void openPersonalArticle(Article article) {
        mRestaurantView.showPersonalArticleUi(article);

    }

    @Override
    public void transToPost() {
        mRestaurantView.transToPost();
    }

    public RestaurantPresenter(RestaurantContract.View restaurantView, Restaurant restaurant, ArrayList<Comment> comments) {


        mRestaurantView = checkNotNull(restaurantView, "detailView cannot be null!");
        mRestaurantView.setPresenter(this);

        mRestaurant = restaurant;
        mComments = comments;
        Log.d("myCommentsBug ", "  RestaurantPresenter  comments.size = " + comments.size());

    }

    @Override
    public void start() {
        mRestaurantView.showRestaurant(mRestaurant, mComments);
    }
}
