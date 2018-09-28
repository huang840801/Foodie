package com.guanhong.foodie.map;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Restaurant;

import java.util.List;

public interface MapContract {

    interface View extends BaseView<Presenter> {

        void showMap();

        void showCustomMarker(Bitmap bitmap);

        void showRestaurantUi(Restaurant restaurant);

        void showMarker(List<LatLng> locations);

//        void setRestaurantData(Restaurant restaurant);
    }

    interface Presenter extends BasePresenter {

        void createCustomMarker(Context context, String title);

        void getRestaurantData(String lat_lng);

        void addMarker();
    }
}
