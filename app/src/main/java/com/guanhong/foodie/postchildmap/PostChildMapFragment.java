package com.guanhong.foodie.postchildmap;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.util.Constants;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostChildMapFragment extends Fragment implements PostChildMapContract.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;

    private PostChildMapContract.Presenter mPresenter;
    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;
    private ImageView mLocation;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 102;
    private boolean getService = false;     //是否已開啟定位服務
    private LocationManager status;


//    private ImageView mBackImageView;

    public static PostChildMapFragment newInstance() {
        return new PostChildMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mPresenter.hideTabLayout();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.showTabLayout();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_child_map, container, false);

//        mBackImageView = rootView.findViewById(R.id.imageView_back_arrow);
        mGoogleMapView = rootView.findViewById(R.id.post_mapView);
        mLocation = rootView.findViewById(R.id.imageView_child_map_my_position);

        mGoogleMapView.onCreate(savedInstanceState);
        mGoogleMapView.onResume();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();

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
                            Toast.makeText(mContext,
                                    "!mGoogleApiClient.isConnected()", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext,
                                "mGoogleApiClient == null", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(mContext, "請開啟定位", Toast.LENGTH_LONG).show();
                    getService = true; //確認開啟定位服務
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //開啟設定頁面
                }
            }
        });

        checkStatus();

//        mBackImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((FoodieActivity) getActivity()).transToPostArticle();
//            }
//        });
    }
    private boolean requestLocationPermissions() {
        Log.d("Permissions", "requestLocationPermissions");

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.d("Permissions", "android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
            return true;
        }

        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(fineLocationPermissionCheck!=PackageManager.PERMISSION_GRANTED&&coarseLocationPermissionCheck!=PackageManager.PERMISSION_GRANTED){
            Log.d("Permissions", "hadFineLocationPermissions() && hadCoarseLocationPermissions()");
            ActivityCompat.requestPermissions((Activity)mContext,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, Constants.REQUEST_FINE_LOCATION_PERMISSION); // your request code
            return true;
        }
        return false;
    }
    private void getMyLocation() {
        try {
            /* code should explicitly check to see if permission is available
            (with 'checkPermission') or explicitly handle a potential 'SecurityException'
             */
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {

                Geocoder geocoder = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);

//                Log.d(Constants.TAG, "  hongtest postchild latitude = " + latLng.latitude);
//                Log.d(Constants.TAG, "  hongtest postchild longitude = " + latLng.longitude);

                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                mPresenter.getAddress(geocoder, latLng);


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

    private void checkStatus() {
        status = (LocationManager) (mContext.getSystemService(Context.LOCATION_SERVICE));
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

    private void requestLocationPermission() {
        // 如果裝置版本是6.0（包含）以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = mContext.checkSelfPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
            int hasPermission2 = mContext.checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION);


            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_FINE_LOCATION_PERMISSION);
            }
//            if (hasPermission2!=PackageManager.PERMISSION_GRANTED){
//                requestPermissions(
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        REQUEST_COARSE_LOCATION_PERMISSION);
//
//            }
            else {
                // 啟動地圖與定位元件

            }
        }
    }

    @Override
    public void setPresenter(PostChildMapContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
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
                        Geocoder geocoder = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);

                        Log.d(Constants.TAG, "  hongtest postchild latitude = " + latLng.latitude);
                        Log.d(Constants.TAG, "  hongtest postchild longitude = " + latLng.longitude);
                        mPresenter.getAddress(geocoder, latLng);

                    }
                });
            }
        });
    }

    @Override
    public void showMap() {
        mGoogleMapView.getMapAsync(this);
    }

    @Override
    public void showDialog(final String addressLine, final LatLng latLng) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_map);

        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.lldialog);
        ll.getLayoutParams().width = 1000;

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        lp.x = 0; // 新位置X坐標
        lp.y = 0; // 新位置Y坐標
        lp.width = 1100; // 寬度
        lp.height = 400; // 高度
//                lp.alpha = 0.7f; // 透明度

        TextView dialogAddress = dialog.findViewById(R.id.textView_dialog_address);
        dialogAddress.setText(addressLine);

        Button btnOk = (Button) dialog.findViewById(R.id.dialog_button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoodieActivity) getActivity()).transToPostArticle(addressLine, latLng);
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(Constants.TAG, " onConnectionSuspended : " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Constants.TAG, " onConnectionFailed : " + connectionResult);

    }
}
