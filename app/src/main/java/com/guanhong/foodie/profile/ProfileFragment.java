package com.guanhong.foodie.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.ProfileArticleAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private ProfileContract.Presenter mPresenter;

    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mArticleCount;
    private RecyclerView mRecyclerView;
    private TextView mPreview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mUserImageView = v.findViewById(R.id.imageView_user);
        mUserName = v.findViewById(R.id.textView_userName);
        mUserEmail = v.findViewById(R.id.textView_userEmail);
        mArticleCount = v.findViewById(R.id.textView_article_count);
        mRecyclerView = v.findViewById(R.id.recyclerview_profile_article);
        mPreview = v.findViewById(R.id.textView_profile_preview);

        mPreview.setVisibility(View.GONE);

        mUserImageView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_user) {

            mPresenter.pickSinglePicture();
        }
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showUserData(User user) {

        mUserName.setText(user.getName());
        mUserEmail.setText(user.getEmail());

        if (!"".equals(user.getImage())) {

            Picasso.get()
                    .load(user.getImage())
                    .into(mUserImageView);
        }

        mPresenter.getMyArticleData();
    }

    @Override
    public void showMyArticles(ArrayList<Article> articleArrayList) {

        if (articleArrayList.size() == 0) {
            mPreview.setVisibility(View.VISIBLE);
        }

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

        mPresenter.transToPersonalArticle(article);
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
