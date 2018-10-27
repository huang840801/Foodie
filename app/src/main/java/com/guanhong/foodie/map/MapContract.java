package com.guanhong.foodie.map;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.BasePresenter;
import com.guanhong.foodie.BaseView;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;
import java.util.List;

public interface MapContract {

    interface View extends BaseView<Presenter> {

        void showMap();

        void setMarkerBitmap(Bitmap bitmap);

        void showMarker(List<LatLng> locations);

        void showRestaurantUi(Restaurant restaurant);
    }

    interface Presenter extends BasePresenter {

        void createCustomMarker(Context context, String title);

        void getRestaurantData(String address);

        void addMarker();
    }
}
