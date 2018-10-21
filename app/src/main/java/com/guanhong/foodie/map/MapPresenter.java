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

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.R;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;

    public MapPresenter(MapContract.View mapView) {

        mMapView = checkNotNull(mapView, "mapView cannot be null");
//        mMapView = mapView;
        mMapView.setPresenter(this);
    }

    @Override
    public void start() {
        mMapView.showMap();
    }

//    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

    @Override
    public void getRestaurantData(String lat_lng) {
        Log.d(Constants.TAG, "hellooo address : " + lat_lng);


        final Restaurant restaurant = new Restaurant();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

        Query query = databaseReference.child(lat_lng);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(Constants.TAG, "MapPresenter getChildrenCount : " + dataSnapshot.getChildrenCount());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Log.d(Constants.TAG, "MapPresenter: " + snapshot);
//                    Log.d(Constants.TAG, "MapPresenter lat_lng : " + snapshot.child("lat_lng").getValue());
//                    Log.d(Constants.TAG, "MapPresenter restaurantName : " + snapshot.child("restaurantName").getValue());
//                    Log.d(Constants.TAG, "MapPresenter starCount : " + snapshot.child("starCount").getValue());

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

                getRestaurantComments(restaurant.getLat_Lng(), restaurant);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getRestaurantComments(final String lat_lng, final Restaurant restaurant) {
//        Log.d("myCommentsBug ", " MapPresenter lat_lng : " + lat_lng);

        final ArrayList<Comment> comments = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("comment");

        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("myCommentsBug ", "  MapPresenter  comments.size = " + comments.size());

                comments.clear();
                for (DataSnapshot snapshot : dataSnapshot.child(lat_lng).getChildren()) {

                    Comment comment = new Comment();
                    Author author = new Author();

//                    Log.d("Comments ", " snapshot : " + snapshot.child("author").child("id").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("author").child("image").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("author").child("name").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("comment").getValue());
//                    Log.d("Comments ", " snapshot : " + snapshot.child("createdTime").getValue());

                    author.setId((String) snapshot.child("author").child("id").getValue());
                    author.setImage((String) snapshot.child("author").child("image").getValue());
                    author.setName((String) snapshot.child("author").child("name").getValue());

                    comment.setAuthor(author);
                    comment.setComment((String) snapshot.child("comment").getValue());
                    comment.setCreatedTime(String.valueOf(snapshot.child("createdTime").getValue()));

                    comments.add(comment);
                }

                Collections.reverse(comments);

                mMapView.showRestaurantUi(restaurant, comments);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void createCustomMarker(Context context, String title) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");
        Query query = databaseReference;

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

//        TextView txt_name = (TextView) marker.findViewById(R.id.textView_marker_count);
//        txt_name.setText(title);

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
                ArrayList<String> restaurantKey = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d(Constants.TAG, "onDataChange: " + snapshot);
                    Log.d(Constants.TAG, "onDataChange: " + snapshot.getKey());

                    restaurantKey.add(snapshot.getKey());

                    String lat;
                    String lng;
                    String key = snapshot.getKey();

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
