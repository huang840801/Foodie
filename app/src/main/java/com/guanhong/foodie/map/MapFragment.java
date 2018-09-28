package com.guanhong.foodie.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.custom.CustomInfoWindowAdapter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;


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
    private Context mContext;

    private GroundOverlay imageOverlay;

    private Fragment mRestaurantFragment;

    private Bitmap mBitmap;

    private Restaurant mRestaurant;



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
        mPresenter = new MapPresenter(this, mContext);

        mGoogleMapView.onCreate(savedInstanceState);
        mGoogleMapView.onResume();


        return rootView;
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mPresenter.start();
//        mGoogleMapView.getMapAsync(this);


//        int[] ints = {3, 4, 5, 1};
//        for (int i = 0; i < ints.length; i++) {
//
//            if(ints[i]<0) {
//                0 -ints[i] =
//            }
//        }


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

        mGoogleMap = googleMap;

        mPresenter.createCustomMarker(mContext, "0");

        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                mPresenter.addMarker();



//                LatLng customMarkerLocationOne = new LatLng(25.042487, 121.564879);
//                LatLng customMarkerLocationTwo = new LatLng(25.042452, 121.563611);
//                LatLng customMarkerLocationThree = new LatLng(25.062609, 121.565398);


//                mGoogleMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).icon(BitmapDescriptorFactory.fromBitmap(mBitmap)));
//                mGoogleMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).icon(BitmapDescriptorFactory.fromBitmap(mBitmap)));
//                mGoogleMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).icon(BitmapDescriptorFactory.fromBitmap(mBitmap)));


//                mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter((FoodieActivity) mContext));
//
//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                builder.include(customMarkerLocationOne);
//                builder.include(customMarkerLocationTwo);
//                LatLngBounds bounds = builder.build();
//
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
//                mGoogleMap.moveCamera(cameraUpdate);
//                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);


            }
        });


        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(test).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void showMarker(List<LatLng> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (LatLng latLng : locations) {
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(mBitmap)));
            builder.include(latLng);
        }

        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter((FoodieActivity) mContext));

//        builder.include(customMarkerLocationOne);
//        builder.include(locations.get(0));
        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);


    }
//    private Bitmap getMarker() {
//        int height = 100;
//        int width = 100;
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_blue);
//        Bitmap b = bitmapDrawable.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

//        return mPresenter.createCustomMarker(mContext, "6");
//    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.d(Constants.TAG, "  lat = " + marker.getPosition().latitude);
        Log.d(Constants.TAG, "  lng = " + marker.getPosition().longitude);


        String lat = marker.getPosition().latitude + "";
        String lng = marker.getPosition().longitude + "";
        String lat_lng = lat + "_" + lng;

        Geocoder geocoder = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);
        try {
            List<Address> addressList = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            Log.d(Constants.TAG, "  address = " + addressList.get(0).getAddressLine(0));

        } catch (IOException e) {
            e.printStackTrace();
        }

        mPresenter.getRestaurantData(lat_lng);


//        mRestaurantFragment = new RestaurantFragment(lat, lng);


    }


    @Override
    public void showMap() {
        Log.d(Constants.TAG, "  MapPresenter  showMap");
        mGoogleMapView.getMapAsync(this);
    }

    @Override
    public void showCustomMarker(Bitmap bitmap) {

        mBitmap = bitmap;
    }

    @Override
    public void showRestaurantUi(Restaurant restaurant) {
        Log.d("restaurant ", " MapFragment : " + restaurant);


        ((FoodieActivity) getActivity()).transToRestaurant(restaurant);


    }


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=24.1674116,120.6679557&radius=5000&types=bank&sensor=true&key=AIzaSyC9nSWENfNXaPrQ9pMFtvxL5NSwbpMiEE

}
