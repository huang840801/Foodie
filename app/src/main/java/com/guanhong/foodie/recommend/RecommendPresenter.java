package com.guanhong.foodie.recommend;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

public class RecommendPresenter implements RecommendContract.Presenter {

    private RecommendContract.View mRecommendView;
    private ArrayList<Restaurant> mRestaurantArrayList;
    private FoodieContract.Presenter mMainPresenter;

    public RecommendPresenter(RecommendContract.View recommendView) {
        mRestaurantArrayList = new ArrayList<>();
        mRecommendView = checkNotNull(recommendView, "mapView cannot be null");
        mRecommendView.setPresenter(this);
    }

    @Override
    public void start() {
        getMyRecommendRestaurant();
    }

    @Override
    public void getMyRecommendRestaurant() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Query query = firebaseDatabase.getReference(Constants.ARTICLE);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mRestaurantArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (Integer.valueOf(snapshot.child("starCount").getValue().toString()) == 5) {
                        Restaurant restaurant = new Restaurant();

                        restaurant.setRestaurantName(snapshot.child(Constants.RESTAURANT_NAME).getValue().toString());
                        restaurant.setRestaurantLocation(snapshot.child(Constants.LOCATION).getValue().toString());
                        restaurant.setStarCount(Integer.parseInt(snapshot.child(Constants.STARCOUNT).getValue().toString()));
                        restaurant.setLat_Lng(snapshot.child(Constants.LAT_LNG).getValue().toString());

                        ArrayList<String> pictures = new ArrayList<>();
                        for (int i = 0; i < snapshot.child(Constants.PICTURES).getChildrenCount(); i++) {
                            pictures.add(snapshot.child(Constants.PICTURES).child(String.valueOf(i)).getValue().toString());
                        }

                        restaurant.setRestaurantPictures(pictures);

                        mRestaurantArrayList.add(restaurant);
                    }
                }
                mRecommendView.showAllRestaurantList(mRestaurantArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
