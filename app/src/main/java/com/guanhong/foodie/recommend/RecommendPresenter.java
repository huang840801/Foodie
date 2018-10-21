package com.guanhong.foodie.recommend;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecommendPresenter implements RecommendContract.Presenter {

    private RecommendContract.View mRecommendView;
    private ArrayList<Restaurant> mRestaurantArrayList;

    public RecommendPresenter(RecommendContract.View recommendView) {
        mRestaurantArrayList = new ArrayList<>();
        mRecommendView = checkNotNull(recommendView, "mapView cannot be null");
        mRecommendView.setPresenter(this);
    }

    @Override
    public void start() {
//        Log.d("Hello", " RecommendPresenter start");
        getMyRecommendRestaurant();
    }

    @Override
    public void getMyRecommendRestaurant() {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("article");

        Query query = databaseReference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("RecommendPresenter", " getChildrenCount = " + dataSnapshot.getChildrenCount());

                mRestaurantArrayList.clear();
//                final int restaurantNum = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.d("RecommendPresenter", " snapshot = "+snapshot);
//                    Log.d("RecommendPresenter", " snapshot getChildrenCount = "+snapshot.getChildrenCount());
                    Log.d("RecommendPresenter", " snapshot = " + snapshot.child("restaurantName").getValue());
                    Log.d("RecommendPresenter", " snapshot = " + snapshot.child("location").getValue());
                    Log.d("RecommendPresenter", " snapshot = " + snapshot.child("starCount").getValue());
                    Log.d("RecommendPresenter", " snapshot = " + snapshot.child("lat_lng").getValue());

                    if (Integer.valueOf(snapshot.child("starCount").getValue().toString()) == 5) {
                        Restaurant restaurant = new Restaurant();

                        restaurant.setRestaurantName(snapshot.child("restaurantName").getValue().toString());
                        restaurant.setRestaurantLocation(snapshot.child("location").getValue().toString());
                        restaurant.setStarCount(Integer.parseInt(snapshot.child("starCount").getValue().toString()));
                        restaurant.setLat_Lng(snapshot.child("lat_lng").getValue().toString());

                        ArrayList<String> pictures = new ArrayList<>();
                        for (int i = 0; i < snapshot.child("pictures").getChildrenCount(); i++) {
                            pictures.add(snapshot.child("pictures").child(String.valueOf(i)).getValue().toString());
                        }

                        restaurant.setRestaurantPictures(pictures);

                        mRestaurantArrayList.add(restaurant);
                    }
                    
                }
//                if (restaurantNum == mRestaurantArrayList.size()) ;
                Log.d("RecommendPresenter", " mRestaurantArrayList = " + mRestaurantArrayList.size());

                mRecommendView.showAllRestaurantList(mRestaurantArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
