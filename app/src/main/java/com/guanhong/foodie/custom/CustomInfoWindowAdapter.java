package com.guanhong.foodie.custom;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity mContext;

    private TextView mRestaurantName;
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private ImageView mStar4;
    private ImageView mStar5;

    private ArrayList<String> mStringRestaurantName = new ArrayList<>();

    public CustomInfoWindowAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(final Marker marker) {

        View view = mContext.getLayoutInflater().inflate(R.layout.custom_marker_info_layout, null);


        mRestaurantName = view.findViewById(R.id.num_textView);
        mStar1 = view.findViewById(R.id.imageView_marker_star1);
        mStar2 = view.findViewById(R.id.imageView_marker_star2);
        mStar3 = view.findViewById(R.id.imageView_marker_star3);
        mStar4 = view.findViewById(R.id.imageView_marker_star4);
        mStar5 = view.findViewById(R.id.imageView_marker_star5);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant");

        Query query = databaseReference.orderByValue();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    double lat = Double.parseDouble(snapshot.child("lat").getValue() +"");
                    double lng = Double.parseDouble(snapshot.child("lng").getValue() +"");


                    if(marker.getPosition().longitude == lng && marker.getPosition().latitude == lat){
                        Log.d(Constants.TAG, "CustomInfoWindowAdapter marker: " + marker.getPosition().longitude);
                        Log.d(Constants.TAG, "CustomInfoWindowAdapter marker: " + marker.getPosition().latitude);
                        Log.d(Constants.TAG, "CustomInfoWindowAdapter marker: " + snapshot.child("restaurantName").getValue());

                        mStringRestaurantName.add(snapshot.child("restaurantName").getValue()+ "");

//                        mRestaurantName.setText(snapshot.child("restaurantName").getValue() +"");
//                        mRestaurantName.setText("Hello");

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d(Constants.TAG, "CustomInfoWindowAdapter String : " + mStringRestaurantName);


//        mRestaurantName.setText(mStringRestaurantName);
        mStar1.setImageResource(R.drawable.star_selected);
        mStar2.setImageResource(R.drawable.star_selected);
        mStar3.setImageResource(R.drawable.star_selected);
        mStar4.setImageResource(R.drawable.star_selected);
        mStar5.setImageResource(R.drawable.star_unselected);


        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
