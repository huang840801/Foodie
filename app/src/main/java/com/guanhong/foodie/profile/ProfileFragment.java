package com.guanhong.foodie.profile;

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
import com.guanhong.foodie.util.SpaceItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private ProfileContract.Presenter mPresenter;

    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mCoinCount;
    private TextView mArticleCount;
    private ImageView mImageViewPost;
    private RecyclerView mRecyclerView;
    private Typeface mTypeface;
    private ArrayList<Article> mArticleArrayList;

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
        mImageViewPost = v.findViewById(R.id.imageView_profile_post_article);
        mRecyclerView = v.findViewById(R.id.recyclerview_profile_article);


        mUserImageView.setOnClickListener(this);
        mImageViewPost.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTypeFace();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private void setTypeFace() {

        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/GenJyuuGothicX-Bold.ttf");
        mUserName.setTypeface(mTypeface);
        mUserEmail.setTypeface(mTypeface);
        mCoinCount.setTypeface(mTypeface);
        mArticleCount.setTypeface(mTypeface);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_user) {
            ((FoodieActivity) getActivity()).pickSinglePicture();
        }
        if (view.getId() == R.id.imageView_profile_post_article) {
            ((FoodieActivity) getActivity()).transToPostArticle();

        }

    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();

    }


    @Override
    public void showUserData(User user) {

        Log.d("showUserData ", " userImage " + user.getImage());
        Log.d("showUserData ", " userImage " + user.getName());
        Log.d("showUserData ", " userImage " + user.getEmail());


        mUserName.setText(user.getName());
        mUserEmail.setText(user.getEmail());
        if (!"".equals(user.getImage())) {

            Log.d("showUserData ", " userImage 有東西");

            Picasso.get()
                    .load(user.getImage())
                    .into(mUserImageView);
        }
        mPresenter.getMyArticleData();

//        ContentResolver cr = mContext.getContentResolver();
//        try {
//            //由抽象資料接口轉換圖檔路徑為Bitmap
//            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(user.getImage())));
//            mUserImageView.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            Log.e("Exception", e.getMessage(), e);
//        }

    }

    @Override
    public void showMyArticles(ArrayList<Article> articleArrayList) {

        mArticleArrayList = articleArrayList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ProfileArticleAdapter(mArticleArrayList, mPresenter));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(40));
        mArticleCount.setText("" + mArticleArrayList.size());
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
        ((FoodieActivity)getActivity()).transToPersonalArticle(article);
    }
}
