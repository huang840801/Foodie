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
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;


public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;
    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();
    private ArrayList<String> mKeyArrayList = new ArrayList<>();

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
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

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
        mSearchView.transToRestaurant(restaurant);
    }

    private void getRestaurantData(ArrayList<String> keyArrayList) {

        Log.d("SearchPresenter", " keyArrayList.size(): " + keyArrayList.size());

        for (int i = 0; i < keyArrayList.size(); i++) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant").child(keyArrayList.get(i));

            Query query = databaseReference;
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final int restaurantNum = (int) dataSnapshot.getChildrenCount();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.child("restaurantName").getValue().toString().contains(mSearchString) || snapshot.child("location").getValue().toString().contains(mSearchString)) {

                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("location").getValue());
                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("restaurantName").getValue());
                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("starCount").getValue());
                            Log.d("SearchPresenter", " getRestaurantData: " + snapshot.child("lat_lng").getValue());
                            Log.d("SearchPresenter", " getRestaurantData dataSnapshot: " + dataSnapshot.getChildrenCount());

                            Restaurant restaurant = new Restaurant();
                            restaurant.setRestaurantName(snapshot.child("restaurantName").getValue().toString());
                            restaurant.setRestaurantLocation(snapshot.child("location").getValue().toString());
                            restaurant.setLat_Lng(snapshot.child("lat_lng").getValue().toString());
                            restaurant.setStarCount(Integer.valueOf(snapshot.child("starCount").getValue().toString()));

                            ArrayList<String> pictures = new ArrayList<>();
                            for (int k = 0; k < snapshot.child("pictures").getChildrenCount(); k++) {
                                pictures.add(snapshot.child("pictures").child(String.valueOf(k)).getValue().toString());
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
}
