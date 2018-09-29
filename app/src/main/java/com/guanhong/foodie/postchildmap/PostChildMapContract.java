package com.guanhong.foodie.postchildmap;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;

public interface PostChildMapContract {
    
    interface View extends BaseView<Presenter>{

        void showMap();

        void showDialog(String addressLine);
    }
    
    interface Presenter extends BasePresenter{

        void hideTabLayout();

        void showTabLayout();

        void getAddress(Geocoder geocoder, LatLng latLng);
    }
}
