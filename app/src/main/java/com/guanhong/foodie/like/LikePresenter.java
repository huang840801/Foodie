package com.guanhong.foodie.like;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.util.UserManager;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

public class LikePresenter implements LikeContract.Presenter {

    private LikeContract.View mLikeView;
    private FoodieContract.Presenter mMainPresenter;

    private ArrayList<String> mRestaurantKeyArrayList = new ArrayList<>();
    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();

    @Override
    public void start() {
        loadRestaurantKey();
    }

    private void loadRestaurantKey() {

        if (UserManager.getInstance().getUserId() == null) {
            return;
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Query query = firebaseDatabase.getReference(Constants.LIKE).child(UserManager.getInstance().getUserId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRestaurantKeyArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

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

        final int num = restaurantKeyArrayList.size();

        for (int i = 0; i < restaurantKeyArrayList.size(); i++) {

            mRestaurantArrayList.clear();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.LIKE).child(UserManager.getInstance().getUserId());

            Query query = databaseReference.child(restaurantKeyArrayList.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Restaurant restaurant = new Restaurant();

                    restaurant.setLat_Lng((String) dataSnapshot.child(Constants.LIKE_LAT_LNG).getValue());
                    restaurant.setRestaurantLocation((String) dataSnapshot.child(Constants.RESTAURANT_LOCATION).getValue());
                    restaurant.setRestaurantName((String) dataSnapshot.child(Constants.RESTAURANT_NAME).getValue());
                    try {
                        restaurant.setStarCount(Integer.valueOf(dataSnapshot.child(Constants.STARCOUNT).getValue().toString()));
                    } catch (NullPointerException e) {

                    }

                    ArrayList<String> pictures = new ArrayList<>();
                    for (int j = 0; j < dataSnapshot.child(Constants.RESTAURANT_PICTURES).getChildrenCount(); j++) {
                        pictures.add(dataSnapshot.child(Constants.RESTAURANT_PICTURES).child(String.valueOf(j)).getValue() + "");
                    }
                    restaurant.setRestaurantPictures(pictures);

                    mRestaurantArrayList.add(restaurant);

                    if (num == mRestaurantArrayList.size()) {
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
        mMainPresenter.transToRestaurant(restaurant);
    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
