package com.guanhong.foodie.map;

import static com.google.common.base.Preconditions.checkNotNull;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import com.guanhong.foodie.mainActivity.FoodieActivity;
import com.guanhong.foodie.custom.CustomInfoWindowAdapter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private MapContract.Presenter mPresenter;

    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;
    private ImageView mLocation;
    private ImageView mPostButton;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager mStatus;
    private Context mContext;
    private Bitmap mBitmap;
    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;
    private String mRestaurantName;
    private String mStarCount;

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mGoogleMapView = rootView.findViewById(R.id.mapView);
        mLocation = rootView.findViewById(R.id.imageView_map_my_position);
        mPostButton = rootView.findViewById(R.id.imageView_map_post_article);
        mContext = getContext();

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
        mGoogleMapView.onCreate(savedInstanceState);
        checkStatus();
        checkNetWork();

        mLocation.setOnClickListener(this);
        mPostButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_map_my_position) {
            requestLocationPermissions();

            if (mStatus.isProviderEnabled(LocationManager.GPS_PROVIDER) && mStatus.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
                if (mGoogleApiClient != null) {
                    if (mGoogleApiClient.isConnected()) {

                        getMyLocation();
                    } else {
                        Toast.makeText(mContext,
                                R.string.map_cannot_connect, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext,
                            R.string.no_map_sevice, Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(mContext, R.string.please_open_gps, Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //開啟設定頁面
            }
        }
        if (view.getId() == R.id.imageView_map_post_article) {
            mPresenter.transToPostArticle();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
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
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
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

                String lat = String.valueOf(marker.getPosition().latitude).replace(".", "@");
                String lng = String.valueOf(marker.getPosition().longitude).replace(".", "@");

                String key = (lat + "_" + lng);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT).child(key);

                Query query = databaseReference.orderByValue();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            double lat = Double.parseDouble(snapshot.child(Constants.LATLNG).child(Constants.LATITUDE).getValue() + "");
                            double lng = Double.parseDouble(snapshot.child(Constants.LATLNG).child(Constants.LONGITUDE).getValue() + "");

                            if (marker.getPosition().longitude == lng && marker.getPosition().latitude == lat) {

                                mRestaurantName = snapshot.child(Constants.RESTAURANT_NAME).getValue() + "";
                                mStarCount = snapshot.child(Constants.STARCOUNT).getValue() + "";

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

            mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(mBitmap)));
            builder.include(latLng);
        }

        mCustomInfoWindowAdapter = new CustomInfoWindowAdapter((FoodieActivity) mContext);
        mGoogleMap.setInfoWindowAdapter(mCustomInfoWindowAdapter);

        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

    }

    @Override
    public void showRestaurantUi(Restaurant restaurant) {

        mPresenter.transToRestaurant(restaurant);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        String lat = String.valueOf(marker.getPosition().latitude).replace(".", "@");
        String lng = String.valueOf(marker.getPosition().longitude).replace(".", "@");
        String latLng = lat + "_" + lng;
        mPresenter.getRestaurantData(latLng);
    }

    @Override
    public void showMap() {
        mGoogleMapView.getMapAsync(this);
    }

    @Override
    public void setMarkerBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private void checkNetWork() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            new AlertDialog.Builder(mContext).setMessage("沒有網路")
                    .setPositiveButton("前往設定網路", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent callNetSettingIntent = new Intent(
                                    Settings.ACTION_WIFI_SETTINGS);
                            startActivity(callNetSettingIntent);
                        }
                    })
                    .show();
        }
    }

    private void getMyLocation() {
        try {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                final Geocoder geocoder = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);

                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        } catch (SecurityException ignored) {
        }
    }

    private void requestLocationPermissions() {

        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED && coarseLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, Constants.REQUEST_FINE_LOCATION_PERMISSION);
        }
    }

    private void checkStatus() {
        mStatus = (LocationManager) (mContext.getSystemService(Context.LOCATION_SERVICE));
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }
}
