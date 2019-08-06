package com.guanhong.foodie.restaurant;

import static com.google.common.base.Preconditions.checkNotNull;

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
import com.guanhong.foodie.adapters.RestaurantMainAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment implements RestaurantContract.View {

    private RestaurantContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private AVLoadingIndicatorView mAvLoadingIndicatorView;

    public RestaurantFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerview_restaurant);
        mAvLoadingIndicatorView = root.findViewById(R.id.AVLoadingIndicatorView_restaurant);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.setTabLayoutVisibility(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
        mAvLoadingIndicatorView.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(RestaurantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showPersonalArticleUi(Article article) {
        mPresenter.transToPersonalArticle(article);
    }

    @Override
    public void transToPost() {
        mPresenter.transToPostArticle();
    }

    @Override
    public void showRestaurantUi(Restaurant restaurant, ArrayList<Comment> comments) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RestaurantMainAdapter(mPresenter, restaurant, comments));
    }

    public static RestaurantFragment newInstance() {
        return new RestaurantFragment();
    }
}
