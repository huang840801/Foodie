package com.guanhong.foodie.like;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("like").child(UserManager.getInstance().getUserId());

        Query query = databaseReference.orderByChild("starCount");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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

        Log.d("LikePresenter ", "restaurantKeyArrayList : " + restaurantKeyArrayList.size());

        for (int i = 0; i < restaurantKeyArrayList.size(); i++) {
            Log.d("LikePresenter ", "restaurantKeyArrayList : " + restaurantKeyArrayList.get(i));


            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("like").child(UserManager.getInstance().getUserId());

            Query query = databaseReference.child(restaurantKeyArrayList.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("lat_Lng").getValue());
                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("restaurantLocation").getValue());
                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("restaurantName").getValue());
                    Log.d("LikePresenter ", "dataSnapshot : " + dataSnapshot.child("starCount").getValue());


//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


//                    for (int i = 0; i < restaurantKeyArrayList.size(); i++) {
//                        if (snapshot.getKey() == restaurantKeyArrayList.get(i)) {
//                        Log.d("LikePresenter ", "loadRestaurant : " + snapshot);
//                        Log.d("LikePresenter ", "loadRestaurant restaurantName : " + snapshot.child("restaurantName").getValue());
//                        Log.d("LikePresenter ", "loadRestaurant restaurantName : " + snapshot.child("restaurantName").getValue());
//                            Log.d("LikePresenter ", "loadRestaurant : "+ snapshot);
//                            Log.d("LikePresenter ", "loadRestaurant : "+ snapshot);


//                        }
//                        }


//                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    public LikePresenter(LikeContract.View likedView) {
        mLikeView = likedView;
        mLikeView.setPresenter(this);
    }
}
