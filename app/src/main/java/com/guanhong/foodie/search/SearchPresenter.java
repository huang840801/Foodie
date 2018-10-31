package com.guanhong.foodie.search;

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
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;


public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;
    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();
    private ArrayList<String> mKeyArrayList = new ArrayList<>();

    private FoodieContract.Presenter mMainPresenter;

    private String mSearchString;

    public SearchPresenter(SearchContract.View searchView) {
        mSearchView = checkNotNull(searchView, "searchView cannot be null");
        mSearchView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void searchArticles(final String s) {

        mKeyArrayList.clear();
        mRestaurantArrayList.clear();
        mSearchString = s;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT);

        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("SearchPresenter", " dataSnapshot " + dataSnapshot.getChildrenCount());
                final int keyNum = (int) dataSnapshot.getChildrenCount();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("SearchPresenter", " mKeyArrayList: " + snapshot.getKey());
                    Log.d("SearchPresenter", " mKeyArrayList: " + snapshot.getChildrenCount());

                    mKeyArrayList.add(snapshot.getKey());
                    Log.d("SearchPresenter", " mKeyArrayList: " + mKeyArrayList.size());

                    if (mKeyArrayList.size() == keyNum) {
                        getRestaurantData(mKeyArrayList);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void transToRestaurant(Restaurant restaurant) {
        mMainPresenter.transToRestaurant(restaurant);
    }

    private void getRestaurantData(ArrayList<String> keyArrayList) {

        Log.d("SearchPresenter", " keyArrayList.size(): " + keyArrayList.size());

        for (int i = 0; i < keyArrayList.size(); i++) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT).child(keyArrayList.get(i));

            Query query = databaseReference;
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final int restaurantNum = (int) dataSnapshot.getChildrenCount();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.child(Constants.RESTAURANT_NAME).getValue().toString().contains(mSearchString) || snapshot.child(Constants.LOCATION).getValue().toString().contains(mSearchString)) {

                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("location").getValue());
                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("restaurantName").getValue());
                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("starCount").getValue());
                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("lat_lng").getValue());
                            Log.d("SearchPresenter", " getRestaurantData dataSnapshot: " + dataSnapshot.getChildrenCount());

                            Restaurant restaurant = new Restaurant();
                            restaurant.setRestaurantName(snapshot.child(Constants.RESTAURANT_NAME).getValue().toString());
                            restaurant.setRestaurantLocation(snapshot.child(Constants.LOCATION).getValue().toString());
                            restaurant.setLat_Lng(snapshot.child(Constants.LAT_LNG).getValue().toString());
                            restaurant.setStarCount(Integer.valueOf(snapshot.child(Constants.STARCOUNT).getValue().toString()));

                            ArrayList<String> pictures = new ArrayList<>();
                            for (int k = 0; k < snapshot.child(Constants.PICTURES).getChildrenCount(); k++) {
                                pictures.add(snapshot.child(Constants.PICTURES).child(String.valueOf(k)).getValue().toString());
                            }

                            restaurant.setRestaurantPictures(pictures);

                            mRestaurantArrayList.add(restaurant);


                        }
                    }
                    if (mRestaurantArrayList.size() == restaurantNum) {

                        mSearchView.showSearchResult(mRestaurantArrayList);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
