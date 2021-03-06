package com.guanhong.foodie.search;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.SearchRestaurantAdapter;
import com.guanhong.foodie.objects.Restaurant;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements SearchContract.View, View.OnClickListener {

    private SearchContract.Presenter mPresenter;

    private Context mContext;
    private EditText mEditTextSearch;
    private ImageView mImageViewSearch;
    private RecyclerView mRecyclerView;
    private TextView mPreviewTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        mContext = getContext();
        mEditTextSearch = v.findViewById(R.id.editText_search);
        mImageViewSearch = v.findViewById(R.id.imageView_magnifier);
        mRecyclerView = v.findViewById(R.id.search_recyclerView);
        mPreviewTextView = v.findViewById(R.id.textView_search_preview);

        return v;
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();
        mImageViewSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageView_magnifier) {
            if (!"".equals(mEditTextSearch.getText().toString())) {

                String searchResult = mEditTextSearch.getText().toString();

                if (searchResult.contains(" ")) {

                    searchResult = searchResult.replace(" ", "");
                }

                mPresenter.searchArticles(searchResult);
            } else {
                Toast.makeText(mContext, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showSearchResult(ArrayList<Restaurant> restaurantArrayList) {

        if (restaurantArrayList.size() == 0) {
            mPreviewTextView.setVisibility(View.VISIBLE);
        } else {
            mPreviewTextView.setVisibility(View.GONE);
        }
        SearchRestaurantAdapter searchRestaurantAdapter = new SearchRestaurantAdapter(mPresenter, restaurantArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(searchRestaurantAdapter);
        searchRestaurantAdapter.notifyDataSetChanged();
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
}
