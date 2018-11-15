package com.guanhong.foodie.map;

import static com.google.common.base.Preconditions.checkNotNull;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.R;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;
    private FoodieContract.Presenter mMainPresenter;

    public MapPresenter(MapContract.View mapView) {

        mMapView = checkNotNull(mapView, "mapView cannot be null");
        mMapView.setPresenter(this);
    }

    @Override
    public void start() {
        mMapView.showMap();
    }


    @Override
    public void getRestaurantData(String latLng) {
        Log.d("myCommentsBug ", " MapPresenter getRestaurantData  ");

        final Restaurant restaurant = new Restaurant();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT);

        Query query = databaseReference.child(latLng);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(Constants.TAG, "MapPresenter getChildrenCount : " + dataSnapshot.getChildrenCount());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    restaurant.setRestaurantLocation(snapshot.child(Constants.LOCATION).getValue().toString());
                    restaurant.setRestaurantName(snapshot.child(Constants.RESTAURANT_NAME).getValue().toString());
                    restaurant.setStarCount(Integer.valueOf(snapshot.child(Constants.STARCOUNT).getValue().toString()));
                    restaurant.setLat_Lng(snapshot.child(Constants.LAT_LNG).getValue().toString());

                    ArrayList<String> pictures = new ArrayList<>();
                    for (int i = 0; i < snapshot.child(Constants.PICTURES).getChildrenCount(); i++) {
                        pictures.add(snapshot.child(Constants.PICTURES).child(String.valueOf(i)).getValue() + "");
                    }
                    restaurant.setRestaurantPictures(pictures);
                }

                mMapView.showRestaurantUi(restaurant);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void createCustomMarker(Context context, String title) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT);
        Query query = databaseReference;

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(50, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        mMapView.setMarkerBitmap(bitmap);

    }

    @Override
    public void addMarker() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT);

        Query query = databaseReference.orderByChild(Constants.CONTENT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<LatLng> locations = new ArrayList<>();
                ArrayList<String> restaurantKey = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d(Constants.TAG, "onDataChange: " + snapshot);
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.getKey());

                    restaurantKey.add(snapshot.getKey());

                    String lat;
                    String lng;
                    String test;
                    String key = snapshot.getKey();

                    test = key.substring(0, key.length()-1);
                    lat = key.substring(0, key.indexOf("_")).replace("@", ".");
                    lng = key.substring(key.indexOf("_") + 1).replace("@", ".");

                    Log.d(Constants.TAG, "onDataChangetest : " + test);
                    Log.d(Constants.TAG, "onDataChangetest : " + key.length());
                    Log.d(Constants.TAG, "onDataChangetest : " + key);
//                    Log.d(Constants.TAG, "onDataChange key : " + key);
                    Log.d(Constants.TAG, "onDataChange lat : " + lat);
                    Log.d(Constants.TAG, "onDataChange lng : " + lng);

                    locations.add((new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
                    }
                mMapView.showMarker(locations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void transToPostArticle() {
        mMainPresenter.transToPostArticle();
    }

    @Override
    public void transToRestaurant(Restaurant restaurant) {
        mMainPresenter.transToRestaurant(restaurant);
    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
