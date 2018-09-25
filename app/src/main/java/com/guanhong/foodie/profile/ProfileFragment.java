package com.guanhong.foodie.profile;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mCoinCount;
    private TextView mArticleCount;

    private Typeface mTypeface;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mContext = getContext();

        mUserImageView = v.findViewById(R.id.imageView_user);
        mUserName = v.findViewById(R.id.textView_userName);
        mUserEmail = v.findViewById(R.id.textView_userEmail);
        mCoinCount = v.findViewById(R.id.textView_coin_count);
        mArticleCount = v.findViewById(R.id.textView_article_count);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTypeFace();

    }

    private void setTypeFace() {

        mTypeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/GenJyuuGothicX-Bold.ttf");
        mUserName.setTypeface(mTypeface);
        mUserEmail.setTypeface(mTypeface);
        mCoinCount.setTypeface(mTypeface);
        mArticleCount.setTypeface(mTypeface);



    }
}
