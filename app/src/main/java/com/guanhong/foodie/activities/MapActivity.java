package com.guanhong.foodie.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.guanhong.foodie.R;
import com.guanhong.foodie.post.PostFragment;
import com.guanhong.foodie.util.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;
    private ImageView mLocation;

    private PostFragment mPostFragment;
    private FoodieActivity mFoodieActivity;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 102;
    private boolean getService = false;     //是否已開啟定位服務
    private LocationManager status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_map);

        mGoogleMapView = findViewById(R.id.post_mapView);
        mLocation = findViewById(R.id.imageView_child_map_my_position);

        mGoogleMapView.onCreate(savedInstanceState);

        checkStatus();

        mGoogleMapView.getMapAsync(this);

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocationPermissions();

                if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) && status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
                    if (mGoogleApiClient != null) {
                        if (mGoogleApiClient.isConnected()) {

                            getMyLocation();
                        } else {
                            Toast.makeText(MapActivity.this,
                                    " Google地圖無法連接 ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MapActivity.this,
                                "mGoogleApiClient == null", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MapActivity.this, "請開啟定位", Toast.LENGTH_LONG).show();
                    getService = true; //確認開啟定位服務
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //開啟設定頁面
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleMapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        mGoogleMapView.onStop();
    }

    private void getMyLocation() {
        try {
            /* code should explicitly check to see if permission is available
            (with 'checkPermission') or explicitly handle a potential 'SecurityException'
             */
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                Geocoder geocoder = new Geocoder(this, Locale.TRADITIONAL_CHINESE);


                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addressList.size() == 0) {
                        Toast.makeText(this, "查無地址", Toast.LENGTH_SHORT).show();
                    } else {

                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                        int height = 100;
                        int width = 100;
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pin_blue);
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);

                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));


//                        showDialog(addressList.get(0).getAddressLine(0), latLng);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.d(Constants.TAG, "getMyLocation: " + String.valueOf(mLastLocation.getLatitude()) + "\n"
                        + String.valueOf(mLastLocation.getLongitude()));

//                Toast.makeText(mContext, "getMyLocation : " + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(Constants.TAG, " mLastLocation == null");

            }
        } catch (SecurityException e) {

            Toast.makeText(this, "SecurityException:\n" + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();

//            Log.d(Constants.TAG, "SecurityException:\n" + String.valueOf(mLastLocation.getLatitude()) + "\n"
//                    + String.valueOf(mLastLocation.getLongitude()));
        }
    }

    private boolean requestLocationPermissions() {
        Log.d("Permissions", "requestLocationPermissions");

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Log.d("Permissions", "android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
            return true;
        }

        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED && coarseLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
//            Log.d("Permissions", "hadFineLocationPermissions() && hadCoarseLocationPermissions()");
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, Constants.REQUEST_FINE_LOCATION_PERMISSION); // your request code
            return true;
        }
        return false;
    }

    private void checkStatus() {
        status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        Log.d(Constants.TAG, " onMapReady clear");
        Log.d(Constants.TAG, " onMapReady  PostChildMapFragment GoogleMapView : " + mGoogleMapView);

//        mGoogleMapView.getLayoutParams().height = 1;
//        mGoogleMapView.getLayoutParams().width = 1;
//        mGoogleMapView.invalidate();
//        mGoogleMapView.requestLayout();

        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                builder.include(new LatLng(25.042487, 121.564879));
                LatLngBounds bounds = builder.build();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mGoogleMap.moveCamera(cameraUpdate);
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo((float) 7.5), 2000, null);
                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Geocoder geocoder = new Geocoder(MapActivity.this, Locale.TRADITIONAL_CHINESE);

                        Log.d(Constants.TAG, "  hongtest postchild latitude = " + latLng.latitude);
                        Log.d(Constants.TAG, "  hongtest postchild longitude = " + latLng.longitude);
//                        mPresenter.getAddress(geocoder, latLng);
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                            if (addressList.size() == 0) {
                                Toast.makeText(MapActivity.this, "查無地址", Toast.LENGTH_SHORT).show();

                            } else {
                                showDialog(addressList.get(0).getAddressLine(0), latLng);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    private void showDialog(final String addressLine, final LatLng latLng) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_map);

        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.lldialog);
        ll.getLayoutParams().width = 1000;
//        ll.getLayoutParams().height = 400;

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
//        lp.x = 0; // 新位置X坐標
//        lp.y = 0; // 新位置Y坐標
        lp.width = 1100; // 寬度
        lp.height = 400; // 高度
//                lp.alpha = 0.7f; // 透明度

        TextView dialogAddress = dialog.findViewById(R.id.textView_dialog_address);
        dialogAddress.setText(addressLine);

        Button btnOk = (Button) dialog.findViewById(R.id.dialog_button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(MapActivity.this, FoodieActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("address", addressLine);
                bundle.putString("lat", String.valueOf(latLng.latitude));
                bundle.putString("lng", String.valueOf(latLng.longitude));

                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();

                dialog.cancel();
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                        Log.d(TAG, "   cancel");
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
