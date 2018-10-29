package com.guanhong.foodie.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.adapters.ProfileArticleAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.util.NormalArticleItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private ProfileContract.Presenter mPresenter;

    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mArticleCount;
    private RecyclerView mRecyclerView;
    private TextView mCoinCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Context context = getContext();

        mUserImageView = v.findViewById(R.id.imageView_user);
        mUserName = v.findViewById(R.id.textView_userName);
        mUserEmail = v.findViewById(R.id.textView_userEmail);
        mCoinCount = v.findViewById(R.id.textView_coin_count);
        mArticleCount = v.findViewById(R.id.textView_article_count);
        mRecyclerView = v.findViewById(R.id.recyclerview_profile_article);

        mUserImageView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("lifecycle", "  ProfileFragment onViewCreated");

        mPresenter.start();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle", "  ProfileFragment onStart");

    }

    @Override
    public void onResume() {
        Log.d("lifecycle", "  ProfileFragment onResume");

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecycle", "  ProfileFragment onPause");

    }

    @Override
    public void onStop() {
        Log.d("lifecycle", "  ProfileFragment onStop");

        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("lifecycle", "  ProfileFragment onDestroy");

        super.onDestroy();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_user) {
            ((FoodieActivity) getActivity()).pickSinglePicture();
        }
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showUserData(User user) {

        Log.d("showUserData ", " userImage " + user.getImage());
        Log.d("showUserData ", " userImage " + user.getName());
        Log.d("showUserData ", " userImage " + user.getEmail());


        mUserName.setText(user.getName());
        mUserEmail.setText(user.getEmail());
        if (!"".equals(user.getImage())) {

            Log.d("showUserData ", " userImage !=null ");

            Picasso.get()
                    .load(user.getImage())
                    .into(mUserImageView);
        }

        mPresenter.getMyArticleData();

    }

    @Override
    public void showMyArticles(ArrayList<Article> articleArrayList) {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ProfileArticleAdapter(articleArrayList, mPresenter));
        mArticleCount.setText(String.valueOf(articleArrayList.size()));
    }

    @Override
    public void showUserNewPicture(Uri userNewPictureUri) {
        Picasso.get()
                .load(userNewPictureUri)
                .placeholder(R.color.profile_main_bg)
                .error(R.color.profile_main_bg)
                .into(mUserImageView);
    }

    @Override
    public void showPersonalArticleUi(Article article) {
        ((FoodieActivity) getActivity()).transToPersonalArticle(article);
    }
}
