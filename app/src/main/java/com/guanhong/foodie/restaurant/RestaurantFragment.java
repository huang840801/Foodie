package com.guanhong.foodie.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.adapters.RestaurantMainAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;


public class RestaurantFragment extends Fragment implements RestaurantContract.View {

    private RestaurantContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private AVLoadingIndicatorView mAVLoadingIndicatorView;

    public RestaurantFragment() {

    }

    public static RestaurantFragment newInstance() {
        return new RestaurantFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerview_restaurant);
        mAVLoadingIndicatorView = root.findViewById(R.id.AVLoadingIndicatorView_restaurant);

//        recyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext()));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(mRestaurantMainAdapter);
//        recyclerView.smoothScrollToPosition(0);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecycle", "  RestaurantFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecycle", "  RestaurantFragment onPause");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
        mAVLoadingIndicatorView.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(RestaurantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        setTabLayoutVisibility(true);
        Log.d("fragmentflow", "  RestaurantFragment onDestroy");
        ((FoodieActivity) getActivity()).setTabLayoutVisibility(true);
        ((FoodieActivity) getActivity()).removeRestaurantFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("lifecycle", "  RestaurantFragment onDestroyView");
//        ((FoodieActivity) getActivity()).removeRestaurantFragment();

    }

    @Override
    public void setTabLayoutVisibility(boolean visible) {
//        ((FoodieActivity) getActivity()).setTabLayoutVisibility(visible);
    }

    @Override
    public void showRestaurant(Restaurant restaurant, ArrayList<Comment> comments) {
        Log.d("myCommentsBug ", "  RestaurantFragment  comments.size = " + comments.size());

//        mRestaurantMainAdapter.updateRestaurantData(restaurant, comments);
//        mRestaurantMainAdapter.notifyDataSetChanged();

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext()));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(new RestaurantMainAdapter(mPresenter, restaurant, comments));
//        mRecyclerView.smoothScrollToPosition(0);


    }

    @Override
    public void showPersonalArticleUi(Article article) {
        ((FoodieActivity) getActivity()).transToPersonalArticle(article);
    }

    @Override
    public void transToPost() {
        ((FoodieActivity) getActivity()).transToPostArticle();
    }

    @Override
    public void showRestaurantUi(Restaurant restaurant, ArrayList<Comment> comments) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RestaurantMainAdapter(mPresenter, restaurant, comments));
//        mRecyclerView.smoothScrollToPosition(0);
    }


}
