package com.guanhong.foodie.map;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.R;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;


public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;

    private Context mContext;

    public MapPresenter(MapContract.View mapView, Context context) {

        mMapView = checkNotNull(mapView, "mapView cannot be null");
//        mMapView = mapView;
        mMapView.setPresenter(this);
        mContext = context;
    }

    @Override
    public void start() {
        mMapView.showMap();
    }


//    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {


    @Override
    public void createCustomMarker(Context context, String title) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        TextView txt_name = (TextView) marker.findViewById(R.id.textView_marker_count);
        txt_name.setText(title);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(50, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        mMapView.showCustomMarker(bitmap);

    }

    @Override
    public void getRestaurantData(String address) {

        final Restaurant restaurant = new Restaurant();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

        Query query = databaseReference.orderByChild("location").equalTo(address);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(Constants.TAG, "onDataChange: " + dataSnapshot);

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("lat_lng").getValue());
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("restaurantName").getValue());
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("starCount").getValue());



                    restaurant.setRestaurantLocation(snapshot.child("location").getValue().toString());
                    restaurant.setRestaurantName(snapshot.child("restaurantName").getValue().toString());
                    restaurant.setStarCount(Integer.valueOf(snapshot.child("starCount").getValue().toString()));
//                    restaurant.setRestaurantPictures(snapshot.child("restaurantName").getValue().toString());
//                    Log.d(Constants.TAG, "onDataChangepictures: " + snapshot.child("pictures").getChildrenCount());

                    ArrayList<String> pictures = new ArrayList<>();
                    for (int i = 0; i < snapshot.child("pictures").getChildrenCount(); i++) {
                        pictures.add(snapshot.child("pictures").child(String.valueOf(i)).getValue() +"");
                    }

//                    Log.d(Constants.TAG, "onDataChangepictures 0 : " + pictures.get(0));
//                    Log.d(Constants.TAG, "onDataChangepictures 0 : " + pictures.get(1));

                    restaurant.setRestaurantPictures(pictures);

                }

                Log.d("restaurant ", " MapPresenter : " + restaurant);

                mMapView.showRestaurantUi(restaurant);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(Constants.TAG, "onDataChange: " + dataSnapshot);
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("lat_lng").getValue());
//                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("restaurantName").getValue());
//                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("starCount").getValue());
//
//
//
//                    restaurant.setRestaurantLocation(snapshot.child("location").getValue().toString());
//                    restaurant.setRestaurantName(snapshot.child("restaurantName").getValue().toString());
//                    restaurant.setStarCount(Integer.valueOf(snapshot.child("starCount").getValue().toString()));
////                    restaurant.setRestaurantPictures(snapshot.child("restaurantName").getValue().toString());
////                    Log.d(Constants.TAG, "onDataChangepictures: " + snapshot.child("pictures").getChildrenCount());
//
//                    ArrayList<String> pictures = new ArrayList<>();
//                    for (int i = 0; i < snapshot.child("pictures").getChildrenCount(); i++) {
//                        pictures.add(snapshot.child("pictures").child(String.valueOf(i)).getValue() +"");
//                    }
//
////                    Log.d(Constants.TAG, "onDataChangepictures 0 : " + pictures.get(0));
////                    Log.d(Constants.TAG, "onDataChangepictures 0 : " + pictures.get(1));
//
//                    restaurant.setRestaurantPictures(pictures);
//
//                }
//
//                Log.d("restaurant ", " MapPresenter : " + restaurant);
//
//
//                mMapView.showRestaurantUi(restaurant);

//                mMapView.setRestaurantData(restaurant);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void addMarker() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

        Query query = databaseReference.orderByValue();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<LatLng> locations = new ArrayList<>();
//                List<String> markerTitle = new ArrayList<>();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("lat").getValue());
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("lng").getValue());
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("restaurantName").getValue());
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("starCount").getValue());
                    locations.add((new LatLng(Double.parseDouble("" + snapshot.child("latLng").child("latitude").getValue()), Double.parseDouble("" + snapshot.child("latLng").child("longitude").getValue()))));
//                    markerTitle.add(snapshot.child("starCount").getValue() +"");
                }

                mMapView.showMarker(locations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
