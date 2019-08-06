package com.guanhong.foodie.search;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

public class SearchPresenter implements SearchContract.Presenter {

    private FoodieContract.Presenter mMainPresenter;

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

        Query query = firebaseDatabase.getReference(Constants.RESTAURANT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final int keyNum = (int) dataSnapshot.getChildrenCount();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    mKeyArrayList.add(snapshot.getKey());

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

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }

    private void getRestaurantData(ArrayList<String> keyArrayList) {

        for (int i = 0; i < keyArrayList.size(); i++) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            Query query = firebaseDatabase.getReference(Constants.RESTAURANT).child(keyArrayList.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final int restaurantNum = (int) dataSnapshot.getChildrenCount();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.child(Constants.RESTAURANT_NAME).getValue().toString().contains(mSearchString) || snapshot.child(Constants.LOCATION).getValue().toString().contains(mSearchString)) {

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
}
