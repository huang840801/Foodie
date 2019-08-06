package com.guanhong.foodie.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private String mStringRestaurantName;
    private int mStarCount = 0;

    private View mView;

    public CustomInfoWindowAdapter(Activity context) {
        Activity activityContext = context;
        mView = activityContext.getLayoutInflater().inflate(R.layout.custom_marker_info_layout, null);

    }

    @Override
    public View getInfoWindow(final Marker marker) {

        TextView textViewRestaurantName = mView.findViewById(R.id.infoWindow_restaurant_textView);
        ImageView star1 = mView.findViewById(R.id.imageView_marker_star1);
        ImageView star2 = mView.findViewById(R.id.imageView_marker_star2);
        ImageView star3 = mView.findViewById(R.id.imageView_marker_star3);
        ImageView star4 = mView.findViewById(R.id.imageView_marker_star4);
        ImageView star5 = mView.findViewById(R.id.imageView_marker_star5);

        textViewRestaurantName.setText(mStringRestaurantName);
        if (mStarCount == 5) {

            star1.setImageResource(R.drawable.new_star_selected);
            star2.setImageResource(R.drawable.new_star_selected);
            star3.setImageResource(R.drawable.new_star_selected);
            star4.setImageResource(R.drawable.new_star_selected);
            star5.setImageResource(R.drawable.new_star_selected);
        }
        if (mStarCount == 4) {

            star1.setImageResource(R.drawable.new_star_selected);
            star2.setImageResource(R.drawable.new_star_selected);
            star3.setImageResource(R.drawable.new_star_selected);
            star4.setImageResource(R.drawable.new_star_selected);
            star5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mStarCount == 3) {
            star1.setImageResource(R.drawable.new_star_selected);
            star2.setImageResource(R.drawable.new_star_selected);
            star3.setImageResource(R.drawable.new_star_selected);
            star4.setImageResource(R.drawable.new_star_unselected);
            star5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mStarCount == 2) {
            star1.setImageResource(R.drawable.new_star_selected);
            star2.setImageResource(R.drawable.new_star_selected);
            star3.setImageResource(R.drawable.new_star_unselected);
            star4.setImageResource(R.drawable.new_star_unselected);
            star5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mStarCount == 1) {
            star1.setImageResource(R.drawable.new_star_selected);
            star2.setImageResource(R.drawable.new_star_unselected);
            star3.setImageResource(R.drawable.new_star_unselected);
            star4.setImageResource(R.drawable.new_star_unselected);
            star5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mStarCount == 0) {
            star1.setImageResource(R.drawable.new_star_unselected);
            star2.setImageResource(R.drawable.new_star_unselected);
            star3.setImageResource(R.drawable.new_star_unselected);
            star4.setImageResource(R.drawable.new_star_unselected);
            star5.setImageResource(R.drawable.new_star_unselected);
        }
        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public void setMarkerData(String restaurantName, String starCount) {
        mStringRestaurantName = restaurantName;
        mStarCount = Integer.valueOf(starCount);
    }
}
