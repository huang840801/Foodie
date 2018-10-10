package com.guanhong.foodie.like;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;
import static com.google.common.base.Preconditions.checkNotNull;


public class LikeFragment extends Fragment implements LikeContract.View{

    private LikeContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_like, container, false);
        return v;
    }

    public static LikeFragment newInstance(){
        return new LikeFragment();
    }

    @Override
    public void setPresenter(LikeContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
        Log.d(Constants.TAG, "  LikeFragment setPresenter");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.TAG, "  LikeFragment onViewCreated");

        mPresenter.start();
    }
}
