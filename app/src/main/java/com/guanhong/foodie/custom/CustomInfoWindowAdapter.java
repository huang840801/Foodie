package com.guanhong.foodie.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity mActivityContext;
    private Context mContext;

    private TextView mTextViewRestaurantName;
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private ImageView mStar4;
    private ImageView mStar5;

    private Handler mHandler;

    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;
    //    private ArrayList<String> mStringRestaurantName = new ArrayList<>();
    private String mStringRestaurantName;
    private int mStarCount =0;

    private boolean isMarkerChange = false;

    private View mView;

    public CustomInfoWindowAdapter(Activity context, Context context1) {
        mActivityContext = context;
        mContext = context1;
        mCustomInfoWindowAdapter = this;
        mView = mActivityContext.getLayoutInflater().inflate(R.layout.custom_marker_info_layout, null);
    }

    @Override
    public View getInfoWindow(final Marker marker) {

        Log.d(Constants.TAG, "CustomInfoWindowAdapter getInfoWindow  " + mStringRestaurantName);
        Log.d(Constants.TAG, "CustomInfoWindowAdapter getInfoWindow  " + mStarCount);

        mTextViewRestaurantName = mView.findViewById(R.id.infoWindow_restaurant_textView);
        mStar1 = mView.findViewById(R.id.imageView_marker_star1);
        mStar2 = mView.findViewById(R.id.imageView_marker_star2);
        mStar3 = mView.findViewById(R.id.imageView_marker_star3);
        mStar4 = mView.findViewById(R.id.imageView_marker_star4);
        mStar5 = mView.findViewById(R.id.imageView_marker_star5);

        mTextViewRestaurantName.setText(mStringRestaurantName);
        if (mStarCount == 5) {
            Log.d(Constants.TAG, "CustomInfoWindowAdapter mStarCount == 5  " );

            mStar1.setImageResource(R.drawable.star_selected);
            mStar2.setImageResource(R.drawable.star_selected);
            mStar3.setImageResource(R.drawable.star_selected);
            mStar4.setImageResource(R.drawable.star_selected);
            mStar5.setImageResource(R.drawable.star_selected);
        }
        if (mStarCount == 4) {
            Log.d(Constants.TAG, "CustomInfoWindowAdapter mStarCount == 4  " );

            mStar1.setImageResource(R.drawable.star_selected);
            mStar2.setImageResource(R.drawable.star_selected);
            mStar3.setImageResource(R.drawable.star_selected);
            mStar4.setImageResource(R.drawable.star_selected);
            mStar5.setImageResource(R.drawable.star_unselected);
        }
        if (mStarCount == 3) {
            mStar1.setImageResource(R.drawable.star_selected);
            mStar2.setImageResource(R.drawable.star_selected);
            mStar3.setImageResource(R.drawable.star_selected);
            mStar4.setImageResource(R.drawable.star_unselected);
            mStar5.setImageResource(R.drawable.star_unselected);
        }
        if (mStarCount == 2) {
            mStar1.setImageResource(R.drawable.star_selected);
            mStar2.setImageResource(R.drawable.star_selected);
            mStar3.setImageResource(R.drawable.star_unselected);
            mStar4.setImageResource(R.drawable.star_unselected);
            mStar5.setImageResource(R.drawable.star_unselected);
        }
        if (mStarCount == 1) {
            mStar1.setImageResource(R.drawable.star_selected);
            mStar2.setImageResource(R.drawable.star_unselected);
            mStar3.setImageResource(R.drawable.star_unselected);
            mStar4.setImageResource(R.drawable.star_unselected);
            mStar5.setImageResource(R.drawable.star_unselected);
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
