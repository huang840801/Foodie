package com.guanhong.foodie.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.custom.CustomInfoWindowAdapter;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;


public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private MapContract.Presenter mPresenter;

    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;
    private ImageView mLocation;
    private ImageView mPostButton;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 102;
    private boolean getService = false;     //是否已開啟定位服務
    private LocationManager mStatus;

    private Context mContext;

    private Bitmap mBitmap;

    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;
    private String mRestaurantName;
    private String mStarCount;

    public static boolean flags = false;

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
        mLocation = rootView.findViewById(R.id.imageView_map_my_position);
        mPostButton = rootView.findViewById(R.id.imageView_map_post_article);
        mContext = getContext();

//mGoogleMapView.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("lifecycle", "  MapFragment onViewCreated");

        mPresenter.start();
        mGoogleMapView.onCreate(savedInstanceState);
        checkStatus();

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocationPermissions();

                if (mStatus.isProviderEnabled(LocationManager.GPS_PROVIDER) && mStatus.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
                    if (mGoogleApiClient != null) {
                        if (mGoogleApiClient.isConnected()) {

                            getMyLocation();
                        } else {
                            Toast.makeText(mContext,
                                    "GoogleApiClient is Connected ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext,
                                "GoogleApiClient is null", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(mContext, "請開啟定位", Toast.LENGTH_LONG).show();
                    getService = true; //確認開啟定位服務
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //開啟設定頁面
                }
            }
        });

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoodieActivity) getActivity()).transToPostArticle();
            }
        });
//        mGoogleMapView.onResume();
    }

    private void getMyLocation() {
        try {
            /* code should explicitly check to see if permission is available
            (with 'checkPermission') or explicitly handle a potential 'SecurityException'
             */
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                Geocoder geocoder = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);

                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

//                int height = 125;
//                int width = 125;
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pin_blue);
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//                Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
//

//                MarkerOptions markerOptions = new MarkerOptions().position(latLng)
//                mGoogleMap.addMarker(new MarkerOptions()
//                        .position(latLng)
//                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
//                );

//                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                Log.d(Constants.TAG, "  MapFragmentgetMyLocation latitude = " + latLng.latitude);
                Log.d(Constants.TAG, "  MapFragmentgetMyLocation  longitude = " + latLng.longitude);
                Log.d(Constants.TAG, "  MapFragmentgetMyLocation  geocoder = " + geocoder);

//                mPresenter.getAddress(geocoder, latLng);


                Log.d(Constants.TAG, "getMyLocation: " + String.valueOf(mLastLocation.getLatitude()) + "\n"
                        + String.valueOf(mLastLocation.getLongitude()));

//                Toast.makeText(mContext, "getMyLocation : " + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {

//                Log.d(Constants.TAG, "mLastLocation == null" + String.valueOf(mLastLocation.getLatitude()) + "\n"
//                        + String.valueOf(mLastLocation.getLongitude()));
//                Toast.makeText(mContext, "LastLocation == null" + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();

            }
        } catch (SecurityException e) {

            Toast.makeText(mContext, "SecurityException:\n" + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();

            Log.d(Constants.TAG, "SecurityException:\n" + String.valueOf(mLastLocation.getLatitude()) + "\n"
                    + String.valueOf(mLastLocation.getLongitude()));
        }
    }

    private boolean requestLocationPermissions() {
        Log.d("Permissions", "requestLocationPermissions");

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.d("Permissions", "android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
            return true;
        }

        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED && coarseLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permissions", "hadFineLocationPermissions() && hadCoarseLocationPermissions()");
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, Constants.REQUEST_FINE_LOCATION_PERMISSION); // your request code
            return true;
        }
        return false;
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


        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //如果沒有授權使用定位就會跳出來這個
            // TODO: Consider calling

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

//            requestLocationPermission(); // 詢問使用者開啟權限
            return;
        }
    }


    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        Log.d("lifecycle", "  MapFragment onStart");

        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleMapView.onResume();
        Log.d("lifecycle", "  MapFragment onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleMapView.onPause();
        Log.d("lifecycle", "  MapFragment onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        Log.d("lifecycle", "  MapFragment onStop");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "  MapFragment onDestroy");
        mGoogleMapView.onDestroy();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d("lifecycle", "  MapFragment onDestroyView" );
//
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mGoogleMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(Constants.TAG, " MapFragment ready");
        Log.d(Constants.TAG, " onMapReady  MapFragment GoogleMapView : "+ mGoogleMapView);

//

            Log.d(Constants.TAG, "MapFragment ready !isHidden");
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

//                Log.d(Constants.TAG, " hongtest MapFragment marker : " + marker.getPosition().latitude);
//                Log.d(Constants.TAG, " hongtest MapFragment marker : " + marker.getPosition().longitude);


                    String lat = String.valueOf(marker.getPosition().latitude).replace(".", "@");
                    String lng = String.valueOf(marker.getPosition().longitude).replace(".", "@");
//                lat = lat.substring(0, Constants.LATLNG_SAVE_DIGITS);
//                lng = lng.substring(0, Constants.LATLNG_SAVE_DIGITS);
                    String key = (lat + "_" + lng);
//                Log.d(Constants.TAG, " hongtest MapFragment: " + lat);
//                Log.d(Constants.TAG, " hongtest MapFragment: " + lng);
//                Log.d(Constants.TAG, " hongtest MapFragment: " + key);
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant").child(key);
//                DatabaseReference databaseReference = firebaseDatabase.getReference("restaurant").child(key);

                    Query query = databaseReference.orderByValue();
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot);
//                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot.child("latLng").getValue());
//                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot.child("latLng").child("latitude").getValue());
//                            Log.d(Constants.TAG, " hongtest MapFragment snapshot : " + snapshot.child("latLng").child("longitude").getValue());

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

//            Log.d(Constants.TAG, " hongtest MapFragment showMarker : " + latLng.latitude);
//            Log.d(Constants.TAG, " hongtest MapFragment showMarker : " + latLng.longitude);

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
    public void showRestaurantUi(Restaurant restaurant) {

        ((FoodieActivity) getActivity()).transToRestaurant(restaurant);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.d(Constants.TAG, "  lat = " + marker.getPosition().latitude);
        Log.d(Constants.TAG, "  lng = " + marker.getPosition().longitude);
        String lat = String.valueOf(marker.getPosition().latitude).replace(".", "@");
        String lng = String.valueOf(marker.getPosition().longitude).replace(".", "@");
        String lat_lng = lat + "_" + lng;
        mPresenter.getRestaurantData(lat_lng);
        Log.d(Constants.TAG, "  lng = " + lat_lng);

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
    public void showRestaurantUi(Restaurant restaurant, ArrayList<Comment> comments) {
        Log.d("myCommentsBug ", "  MapFragment  comments.size = " + comments.size());
        ((FoodieActivity) getActivity()).transToRestaurant(restaurant, comments);
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

}
