package com.guanhong.foodie.postchildmap;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.util.Constants;

import java.io.IOException;
import java.util.List;

public class PostChildMapPresenter implements PostChildMapContract.Presenter{

    private PostChildMapContract.View mPostChildMapView;




    public PostChildMapPresenter(PostChildMapContract.View postChildMapView) {

        mPostChildMapView = postChildMapView;
        mPostChildMapView.setPresenter(this);
    }

    @Override
    public void start() {
        mPostChildMapView.showMap();
    }

    @Override
    public void hideTabLayout() {

    }

    @Override
    public void showTabLayout() {

    }

    @Override
    public void getAddress(Geocoder geocoder, LatLng latLng) {

        try{

            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//            Log.d(Constants.TAG, "  地址 = " + addressList.get(0).getAddressLine(0));
            mPostChildMapView.showDialog(addressList.get(0).getAddressLine(0), latLng);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
