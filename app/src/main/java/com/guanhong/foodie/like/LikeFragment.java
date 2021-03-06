package com.guanhong.foodie.like;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.LikeArticleAdapter;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public class LikeFragment extends Fragment implements LikeContract.View {

    private LikeContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_like, container, false);

        mContext = getContext();
        mRecyclerView = v.findViewById(R.id.recyclerview_like);
        mTextView = v.findViewById(R.id.textView_like_fragment);
        return v;
    }

    @Override
    public void setPresenter(LikeContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();
    }

    @Override
    public void showLikeArticleList(ArrayList<Restaurant> restaurantArrayList) {

        if (restaurantArrayList.size() > 0) {
            mTextView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.VISIBLE);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new LikeArticleAdapter(mPresenter, restaurantArrayList));
    }

    public static LikeFragment newInstance() {
        return new LikeFragment();
    }
}
