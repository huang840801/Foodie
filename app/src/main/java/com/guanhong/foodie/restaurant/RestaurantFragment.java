package com.guanhong.foodie.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.adapters.RestaurantDetailAdapter;
import com.guanhong.foodie.objects.Restaurant;

import static com.google.common.base.Preconditions.checkNotNull;


public class RestaurantFragment extends Fragment implements RestaurantContract.View {

    private RestaurantContract.Presenter mPresenter;
    private RestaurantDetailAdapter mRestaurantDetailAdapter;

    private Restaurant mRestaurant;


    public RestaurantFragment() {

    }

    public static RestaurantFragment newInstance() {
        return new RestaurantFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mPresenter = new RestaurantPresenter(this);
        mPresenter.hideTabLayout();
        mRestaurantDetailAdapter = new RestaurantDetailAdapter(new Restaurant(), mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_restaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRestaurantDetailAdapter);
        recyclerView.smoothScrollToPosition(0);

//        recyclerView.scrollToPosition(0);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setPresenter(RestaurantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.showTabLayout();
    }


    @Override
    public void setTabLayoutVisibility(boolean visible) {
        ((FoodieActivity) getActivity()).setTabLayoutVisibility(visible);
    }

    @Override
    public void showRestaurant(Restaurant restaurant) {
        mRestaurantDetailAdapter.updateRestaurant(restaurant);
    }


}