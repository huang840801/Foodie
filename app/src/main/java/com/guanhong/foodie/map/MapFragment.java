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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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

    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;
    private String mRestaurantName;
    private String mStarCount;

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
        mPresenter.createCustomMarker(mContext, "");

        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mPresenter.addMarker();
            }
        });

        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Log.d(Constants.TAG, "onMarkerClick: ");

                Log.d(Constants.TAG, " hongtest MapFragment marker : " + marker.getPosition().latitude);
                Log.d(Constants.TAG, " hongtest MapFragment marker : " + marker.getPosition().longitude);


                String lat = String.valueOf(marker.getPosition().latitude).replace(".", "@");
                String lng = String.valueOf(marker.getPosition().longitude).replace(".", "@");
//                lat = lat.substring(0, Constants.LATLNG_SAVE_DIGITS);
//                lng = lng.substring(0, Constants.LATLNG_SAVE_DIGITS);
                String key = (lat + "_" + lng);
                Log.d(Constants.TAG, " hongtest MapFragment: " + lat);
                Log.d(Constants.TAG, " hongtest MapFragment: " + lng);
                Log.d(Constants.TAG, " hongtest MapFragment: " + key);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant").child(key);
//                DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant").child(key);

                Query query = databaseReference.orderByValue();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot);
                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot.child("latLng").getValue());
                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot.child("latLng").child("latitude").getValue());
                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot.child("latLng").child("longitude").getValue());

                            double lat = Double.parseDouble(snapshot.child("latLng").child("latitude").getValue() + "");
                            double lng = Double.parseDouble(snapshot.child("latLng").child("longitude").getValue() + "");

                            if (marker.getPosition().longitude == lng && marker.getPosition().latitude == lat) {

                                mRestaurantName = snapshot.child("restaurantName").getValue() + "";
                                mStarCount = snapshot.child("starCount").getValue() + "";

                                mCustomInfoWindowAdapter.setMarkerData(mRestaurantName, mStarCount);

                                marker.showInfoWindow();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                return false;
            }
        });
    }

    @Override
    public void showMarker(List<LatLng> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (LatLng latLng : locations) {

            Log.d(Constants.TAG, " hongtest MapFragment showMarker : " + latLng.latitude);
            Log.d(Constants.TAG, " hongtest MapFragment showMarker : " + latLng.longitude);
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(mBitmap)));
            builder.include(latLng);
        }

        mCustomInfoWindowAdapter = new CustomInfoWindowAdapter((FoodieActivity) mContext, mContext);
        mGoogleMap.setInfoWindowAdapter(mCustomInfoWindowAdapter);

        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.d(Constants.TAG, "  lat = " + marker.getPosition().latitude);
        Log.d(Constants.TAG, "  lng = " + marker.getPosition().longitude);
        String lat = String.valueOf(marker.getPosition().latitude).replace(".", "@");
        String lng = String.valueOf(marker.getPosition().longitude).replace(".", "@");
        String latlng = lat + "_" + lng;
        mPresenter.getRestaurantData(latlng);
        Log.d(Constants.TAG, "  lng = " + latlng);

//        Geocoder geocoder = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);
//        try {
//            List<Address> addressList = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
//            Log.d(Constants.TAG, "  address = " + addressList.get(0).getAddressLine(0));
//            mPresenter.getRestaurantData(addressList.get(0).getAddressLine(0));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void showMap() {
//        Log.d(Constants.TAG, "  MapPresenter  showMap");
        mGoogleMapView.getMapAsync(this);
    }

    @Override
    public void setMarkerBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public void showRestaurantUi(Restaurant restaurant) {
        Log.d("restaurant ", " MapFragment : " + restaurant);
        ((FoodieActivity) getActivity()).transToRestaurant(restaurant);
    }


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=24.1674116,120.6679557&radius=5000&types=bank&sensor=true&key=AIzaSyC9nSWENfNXaPrQ9pMFtvxL5NSwbpMiEE

}
