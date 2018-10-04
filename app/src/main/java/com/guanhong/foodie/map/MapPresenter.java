package com.guanhong.foodie.map;


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
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
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
    public void getRestaurantData(String address) {
        Log.d(Constants.TAG, "hellooo address : " + address);


        final Restaurant restaurant = new Restaurant();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

        Query query = databaseReference.child(address);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(Constants.TAG, "hellooo: " + dataSnapshot);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(Constants.TAG, "hellooo: " + snapshot);
                    Log.d(Constants.TAG, "hellooo: " + snapshot.child("lat_lng").getValue());
                    Log.d(Constants.TAG, "hellooo: " + snapshot.child("restaurantName").getValue());
                    Log.d(Constants.TAG, "hellooo: " + snapshot.child("starCount").getValue());


                    restaurant.setRestaurantLocation(snapshot.child("location").getValue().toString());
                    restaurant.setRestaurantName(snapshot.child("restaurantName").getValue().toString());
                    restaurant.setStarCount(Integer.valueOf(snapshot.child("starCount").getValue().toString()));
                    restaurant.setLat_Lng(snapshot.child("lat_lng").getValue().toString());
//                    restaurant.setRestaurantPictures(snapshot.child("restaurantName").getValue().toString());
//                    Log.d(Constants.TAG, "onDataChangepictures: " + snapshot.child("pictures").getChildrenCount());

                    ArrayList<String> pictures = new ArrayList<>();
                    for (int i = 0; i < snapshot.child("pictures").getChildrenCount(); i++) {
                        pictures.add(snapshot.child("pictures").child(String.valueOf(i)).getValue() + "");
                    }

//                    Log.d(Constants.TAG, "onDataChangepictures 0 : " + pictures.get(0));
//                    Log.d(Constants.TAG, "onDataChangepictures 0 : " + pictures.get(1));

                    restaurant.setRestaurantPictures(pictures);

                }

                Log.d("restaurant ", " MapPresenter : " + restaurant);

                mMapView.showRestaurantUi(restaurant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


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

        mMapView.setMarkerBitmap(bitmap);

    }

    @Override
    public void addMarker() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

        Query query = databaseReference.orderByChild("content");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<LatLng> locations = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    Log.d(Constants.TAG, "onDataChange: " + snapshot);
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.getKey());

                    String lat;
                    String lng;
                    String key = snapshot.getKey();

//                    String s = "25.111_120.222e";
//                    String a,b;
//                    a = s.substring(0,s.indexOf("_"));
//                    Log.d("ooooo", " a : " + a);
//
//                    b = s.substring(s.indexOf("_"),s.indexOf("e")).replace("_", "");
//
//                    Log.d("ooooo", " b : " + b);
//                    Log.d(Constants.TAG, "onCreateonCreate: " + s.substring(s.indexOf("_")+1));


                    lat = key.substring(0, key.indexOf("_")).replace("@", ".");
                    lng = key.substring(key.indexOf("_") + 1).replace("@", ".");
//                    lng = key.substring(key.indexOf("_") + 1, key.indexOf("e") ).replace("@", ".");

                    Log.d(Constants.TAG, "onDataChange lat : " + lat);
                    Log.d(Constants.TAG, "onDataChange lng : " + lng);

//                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("restaurantName").getValue());
//                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("starCount").getValue());
//                    Log.d(Constants.TAG, "onDataChange: " + snapshot.child("latLng").getValue());
                    locations.add((new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));


//                    locations.add((new LatLng(Double.parseDouble("" + snapshot.child("latLng").child("latitude").getValue()), Double.parseDouble("" + snapshot.child("latLng").child("longitude").getValue()))));
                }

                mMapView.showMarker(locations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
