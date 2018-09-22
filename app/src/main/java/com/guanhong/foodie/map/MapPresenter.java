package com.guanhong.foodie.map;


import android.annotation.SuppressLint;
import android.util.Log;

import com.guanhong.foodie.util.Constants;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;

    @SuppressLint("RestrictedApi")
    public MapPresenter(MapContract.View mapView) {

        mMapView = checkNotNull(mapView, "mapView cannot be null");
//        mMapView = mapView;
        mMapView.setPresenter(this);
//        Log.d(Constants.TAG, "  mMapView = "+mMapView);

    }

    @Override
    public void start() {
        mMapView.showMap();
    }
}
