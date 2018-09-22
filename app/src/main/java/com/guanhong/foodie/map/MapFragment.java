package com.guanhong.foodie.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guanhong.foodie.R;
import com.guanhong.foodie.restaurant.RestaurantFragment;
import com.guanhong.foodie.util.Constants;

import static android.support.v4.util.Preconditions.checkNotNull;


public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private MapContract.Presenter mPresenter;

    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;

    // Google API用戶端物件
    private GoogleApiClient googleApiClient;

    // Location請求物件
    private LocationRequest locationRequest;

    // 記錄目前最新的位置
    private Location currentLocation;

    // 顯示目前與儲存位置的標記物件
    private Marker currentMarker, itemMarker;
    private TextView mTextView;
    private Context mContext;

    private GroundOverlay imageOverlay;

    private Fragment mRestaurantFragment;

    public MapFragment() {

    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mGoogleMapView = (MapView) rootView.findViewById(R.id.mapView);
        mContext = getContext();
        mPresenter = new MapPresenter(this);

        mGoogleMapView.onCreate(savedInstanceState);
        mGoogleMapView.onResume();

        return rootView;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mPresenter.start();
//        mGoogleMapView.getMapAsync(this);

////////////////////////////////////////////////////////////////////////////////
//        int num = 29;
//        boolean hasAnswer = false;
//        for (int i = 1; i < num / 2; i++) {
//            if (i * i == num) {
//                Log.d(Constants.TAG + "根號 ", num + "開根號 =  " + i);
//                hasAnswer = true;
//                break;
//            }
//        }
//        if (!hasAnswer) {
//            Log.d(Constants.TAG + "根號 ", num + "開根號 = 無解!");
//        }
////////////////////////////////////////////////////////////////////////////////
//        double num = 10;
//        double p = 0, n = 0;
//        double i;
//        for (i = 1; i < num; i = i + 0.1) {
//            if (num - (i * i) <= 0) {
//                p = i - 0.1;
//                n = i;
//                break;
//            }
//        }
//
//        if (num - (p * p) < (n * n) - num) {
//            Log.d(Constants.TAG + "根號 ", num + "開根號 = p =  " + p);
//        } else {
//            Log.d(Constants.TAG + "根號 ", num + "開根號 = n =  " + n);
//        }

////////////////////////////////////////////////////////////////////////////////

    }


    @Override
    public void onResume() {
        super.onResume();
        mGoogleMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mGoogleMapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
//                googleMap.setMyLocationEnabled(true);

        mGoogleMap = googleMap;
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View markerd = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
                marker.setTitle("AppWorks School");

                return markerd;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        LatLng test = new LatLng(25.042609, 121.565398);
//        addMyLocationIcon(test);
//        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.stin);
//        Bitmap newIcon = pr(myIcon);

//        View marker = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
//        TextView num = marker.findViewById(R.id.num_textView);
//        num.setText("35");




        Marker customMarker = googleMap.addMarker(new MarkerOptions()
                .position(test)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .icon(BitmapDescriptorFactory.fromBitmap(getMarker()))
                .title("AppWorks School")
                .flat(true)
                .snippet("Hello"));


        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(test).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleMap.setOnInfoWindowClickListener(this);
    }

    private Bitmap getMarker() {
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_blue);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        return smallMarker;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(Constants.TAG, "  onInfoWindowClick");

        mRestaurantFragment = new RestaurantFragment();


    }


    @Override
    public void showMap() {
        Log.d(Constants.TAG, "  MapPresenter  showMap");
        mGoogleMapView.getMapAsync(this);
    }

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=24.1674116,120.6679557&radius=5000&types=bank&sensor=true&key=AIzaSyC9nSWENfNXaPrQ9pMFtvxL5NSwbpMiEE

}
