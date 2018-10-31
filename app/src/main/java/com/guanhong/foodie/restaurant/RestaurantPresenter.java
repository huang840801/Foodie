package com.guanhong.foodie.restaurant;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.FoodieContract;
import com.guanhong.foodie.FoodiePresenter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.Collections;


public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.View mRestaurantView;
    private Restaurant mRestaurant;

    private FoodieContract.Presenter mMainPresenter;


    public RestaurantPresenter(RestaurantContract.View restaurantView, Restaurant restaurant) {

        Log.d("RestaurantPresenter ", "  mRestaurant.getLat_Lng()   = " + restaurant.getLat_Lng());


        mRestaurantView = checkNotNull(restaurantView, "detailView cannot be null!");
        mRestaurantView.setPresenter(this);

        mRestaurant = restaurant;
    }

    @Override
    public void openPersonalArticle(Article article) {
        mRestaurantView.showPersonalArticleUi(article);

    }

    @Override
    public void transToPost() {
        mRestaurantView.transToPost();
    }

    @Override
    public void setTabLayoutVisibility(boolean isTabLayoutVisibility) {
        mMainPresenter.setTabLayoutVisibility(isTabLayoutVisibility);
    }

    @Override
    public void transToPersonalArticle(Article article) {
        mMainPresenter.transToPersonalArticle(article);
    }

    @Override
    public void transToPostArticle() {
        mMainPresenter.transToPostArticle();
    }

    @Override
    public void start() {
        getRestaurantComments(mRestaurant.getLat_Lng());
        Log.d("getRestaurantComments ", "  mRestaurant.getLat_Lng()   = " + mRestaurant.getLat_Lng());
        Log.d("getRestaurantComments ", "  mRestaurant.getLat_Lng()   = " + mRestaurant.getRestaurantName());

    }

    private void getRestaurantComments(final String latLng) {

        Log.d("myCommentsBug ", " MapPresenter getRestaurantComments  ");

        final ArrayList<Comment> comments = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.COMMENT);

        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("myCommentsBug ", "  MapPresenter onDataChange comments.size = " + comments.size());

                comments.clear();
                for (DataSnapshot snapshot : dataSnapshot.child(latLng).getChildren()) {

                    final Comment comment = new Comment();
                    Author author = new Author();

                    author.setId((String) snapshot.child(Constants.AUTHOR).child(Constants.ID).getValue());
                    author.setImage((String) snapshot.child(Constants.AUTHOR).child(Constants.IMAGE).getValue());
                    author.setName((String) snapshot.child(Constants.AUTHOR).child(Constants.NAME).getValue());

                    comment.setAuthor(author);
                    comment.setComment((String) snapshot.child(Constants.COMMENT).getValue());
                    comment.setCreatedTime(String.valueOf(snapshot.child(Constants.CREATEDTIME).getValue()));

                    comments.add(comment);
                }

                Collections.reverse(comments);

                mRestaurantView.showRestaurantUi(mRestaurant, comments);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
