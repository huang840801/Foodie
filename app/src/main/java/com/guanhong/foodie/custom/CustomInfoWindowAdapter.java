package com.guanhong.foodie.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.util.Constants;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity mActivityContext;
    private Context mContext;

    private TextView mRestaurantName;
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private ImageView mStar4;
    private ImageView mStar5;

    private Handler mHandler;

//    private ArrayList<String> mStringRestaurantName = new ArrayList<>();
    private String mStringRestaurantName;

    public CustomInfoWindowAdapter(Activity context, Context context1) {
        mActivityContext = context;
        mContext = context1;
    }

    @Override
    public View getInfoWindow(final Marker marker) {



        View view = mActivityContext.getLayoutInflater().inflate(R.layout.custom_marker_info_layout, null);


        mRestaurantName = view.findViewById(R.id.infoWindow_restaurant_textView);
        mStar1 = view.findViewById(R.id.imageView_marker_star1);
        mStar2 = view.findViewById(R.id.imageView_marker_star2);
        mStar3 = view.findViewById(R.id.imageView_marker_star3);
        mStar4 = view.findViewById(R.id.imageView_marker_star4);
        mStar5 = view.findViewById(R.id.imageView_marker_star5);

        searchName();

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

//                        mStringRestaurantName.add(snapshot.child("restaurantName").getValue()+ "");


                        mStringRestaurantName = snapshot.child("restaurantName").getValue()+ "";
                        Log.d(Constants.TAG, "CustomInfoWindowAdapter String : " + mStringRestaurantName);


                        Message message = mHandler.obtainMessage();
                        message.what = 1;
                        mHandler.sendMessage(message);


//                        Activity activity = (Activity)mRestaurantName.getContext();
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRestaurantName.setText(mStringRestaurantName);
//                            }
//                        });

//                        ((FoodieActivity)mContext).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRestaurantName.setText(mStringRestaurantName);
//                            }
//                        });

//                        new Handler(mContext.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRestaurantName.setText(mStringRestaurantName);
//                            }
//                        });

//                        mRestaurantName.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRestaurantName.setText(mStringRestaurantName);
//                            }
//                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Log.d(Constants.TAG, "CustomInfoWindowAdapter String : " + mStringRestaurantName);

         mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(msg.what == 1){
                    Log.d(Constants.TAG, "CustomInfoWindowAdapter String : " + mStringRestaurantName);

                    mRestaurantName.setText(mStringRestaurantName);
                }

            }
        };

//        mRestaurantName.setText(mStringRestaurantName);
//        mStar1.setImageResource(R.drawable.star_selected);
//        mStar2.setImageResource(R.drawable.star_selected);
//        mStar3.setImageResource(R.drawable.star_selected);
//        mStar4.setImageResource(R.drawable.star_selected);
//        mStar5.setImageResource(R.drawable.star_unselected);


        return view;
    }

    private void searchName() {
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
