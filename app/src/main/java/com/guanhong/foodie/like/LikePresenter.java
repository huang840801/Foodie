package com.guanhong.foodie.like;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Preconditions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;


public class LikePresenter implements LikeContract.Presenter {

    private LikeContract.View mLikeView;

    private ArrayList<String> mRestaurantKeyArrayList = new ArrayList<>();
    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();

    @Override
    public void start() {
        Log.d("LikePresenter ", "start: ");
        loadRestaurantKey();
    }

    private void loadRestaurantKey() {

        Log.d("LikePresenter ", "userid: " + UserManager.getInstance().getUserId());

//        mRestaurantKeyArrayList.clear();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("like").child(UserManager.getInstance().getUserId());

        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRestaurantKeyArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.d("LikePresenter ", "loadRestaurantKey : " + snapshot);
                    Log.d("LikePresenter ", "loadRestaurantKey : " + snapshot.getKey());
//                    Log.d("LikePresenter ", "loadRestaurantKey : "+ snapshot.child("starCount").getValue());
//                    Log.d("LikePresenter ", "loadRestaurantKey : "+ snapshot.child("restaurantName").getValue());
                    mRestaurantKeyArrayList.add(snapshot.getKey());


                }
                loadRestaurant(mRestaurantKeyArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadRestaurant(final ArrayList<String> restaurantKeyArrayList) {

//        Log.d("LikePresenter ", "restaurantKeyArrayList : " + restaurantKeyArrayList.size());
       final int num = restaurantKeyArrayList.size();

        for (int i = 0; i < restaurantKeyArrayList.size(); i++) {
//            Log.d("LikePresenter ", "restaurantKeyArrayList : " + restaurantKeyArrayList.get(i));

            mRestaurantArrayList.clear();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("like").child(UserManager.getInstance().getUserId());

            Query query = databaseReference.child(restaurantKeyArrayList.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Restaurant restaurant = new Restaurant();

//                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("lat_Lng").getValue());
//                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("restaurantLocation").getValue());
//                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("restaurantName").getValue());
//                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("starCount").getValue());
//                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("restaurantPictures").getValue());

                    restaurant.setLat_Lng((String) dataSnapshot.child("lat_Lng").getValue());
                    restaurant.setRestaurantLocation((String) dataSnapshot.child("restaurantLocation").getValue());
                    restaurant.setRestaurantName((String) dataSnapshot.child("restaurantName").getValue());
                    try {
                        restaurant.setStarCount(Integer.valueOf(dataSnapshot.child("starCount").getValue().toString()));
                    }catch (NullPointerException e){

                    }

                    ArrayList<String> pictures = new ArrayList<>();
                    for (int j = 0; j < dataSnapshot.child("restaurantPictures").getChildrenCount(); j++) {
                        pictures.add(dataSnapshot.child("restaurantPictures").child(String.valueOf(j)).getValue() + "");
                    }
                    restaurant.setRestaurantPictures(pictures);

                    mRestaurantArrayList.add(restaurant);

                    if(num ==mRestaurantArrayList.size()){
                        mLikeView.showLikeArticleList(mRestaurantArrayList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

    public LikePresenter(LikeContract.View likedView) {
        mLikeView = Preconditions.checkNotNull(likedView, "likeView cannot be null");
        mLikeView.setPresenter(this);
    }

    @Override
    public void transToRestaurant(Restaurant restaurant) {
        mLikeView.transToRestaurant(restaurant);
    }
}
