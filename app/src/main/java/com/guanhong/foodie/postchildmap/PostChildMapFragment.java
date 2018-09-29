package com.guanhong.foodie.postchildmap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostChildMapFragment extends Fragment implements PostChildMapContract.View, OnMapReadyCallback {

    private Context mContext;

    private PostChildMapContract.Presenter mPresenter;
    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;

    private ImageView mBackImageView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_child_map, container, false);

        mBackImageView = rootView.findViewById(R.id.imageView_back_arrow);
        mGoogleMapView = rootView.findViewById(R.id.post_mapView);

        mGoogleMapView.onCreate(savedInstanceState);
        mGoogleMapView.onResume();


        return rootView;    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoodieActivity)getActivity()).transToPostArticle();
            }
        });
    }

    @Override
    public void setPresenter(PostChildMapContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
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
                mPresenter.getAddress(geocoder, latLng);

            }
        });



    }

    @Override
    public void showMap() {
        mGoogleMapView.getMapAsync(this);
    }

    @Override
    public void showDialog(final String addressLine) {
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
                ((FoodieActivity)getActivity()).transToPostArticle(addressLine);
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
