package com.guanhong.foodie.recommend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanhong.foodie.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecommendFragment extends Fragment implements RecommendContract.View{

    private RecommendContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        return v;
    }
    public static RecommendFragment newInstance(){
        return new RecommendFragment();
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();
    }
}
