package com.guanhong.foodie.liked;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanhong.foodie.R;
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.util.Constants;
import static com.google.common.base.Preconditions.checkNotNull;


public class LikedFragment extends Fragment implements LikedContract.View{

    private LikedContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_like, container, false);
        return v;
    }

    public static LikedFragment newInstance(){
        return new LikedFragment();
    }

    @Override
    public void setPresenter(LikedContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

        Log.d(Constants.TAG, "  transToLiked  setPresenter " + mPresenter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(Constants.TAG, "  transToLiked " + mPresenter);

        Log.d("UserManager", " email = "+ UserManager.getInstance().getUserEmail());
        Log.d("UserManager", " id = "+ UserManager.getInstance().getUserId());
        Log.d("UserManager", " image = "+ UserManager.getInstance().getUserImage());
        Log.d("UserManager", " name = "+ UserManager.getInstance().getUserName());

        mPresenter.start();
    }
}
