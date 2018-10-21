package com.guanhong.foodie.restaurant;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkNotNull;


public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.View mRestaurantView;
    private Restaurant mRestaurant;
    private ArrayList<Comment> mComments;

    public RestaurantPresenter(RestaurantContract.View restaurantView, Restaurant restaurant) {

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

    public RestaurantPresenter(RestaurantContract.View restaurantView, Restaurant restaurant, ArrayList<Comment> comments) {


        mRestaurantView = checkNotNull(restaurantView, "detailView cannot be null!");
        mRestaurantView.setPresenter(this);

        mRestaurant = restaurant;
        mComments = comments;
        Log.d("myCommentsBug ", "  RestaurantPresenter  comments.size = " + comments.size());

    }

    @Override
    public void start() {

        getRestaurantComments(mRestaurant.getLat_Lng());
//        mRestaurantView.showRestaurant(mRestaurant, mComments);
    }

    private void getRestaurantComments(final String lat_lng) {

        Log.d("myCommentsBug ", " MapPresenter getRestaurantComments  " );

        final ArrayList<Comment> comments = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("comment");

        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("myCommentsBug ", "  MapPresenter onDataChange comments.size = " + comments.size());

                comments.clear();
                for (DataSnapshot snapshot : dataSnapshot.child(lat_lng).getChildren()) {

                    Comment comment = new Comment();
                    Author author = new Author();

//                    Log.d("Comments ", " snapshot : " + snapshot.child("author").child("id").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("author").child("image").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("author").child("name").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("comment").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("createdTime").getValue());

                    author.setId((String) snapshot.child("author").child("id").getValue());
                    author.setImage((String) snapshot.child("author").child("image").getValue());
                    author.setName((String) snapshot.child("author").child("name").getValue());

                    comment.setAuthor(author);
                    comment.setComment((String) snapshot.child("comment").getValue());
                    comment.setCreatedTime(String.valueOf(snapshot.child("createdTime").getValue()));

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
}
