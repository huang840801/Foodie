package com.guanhong.foodie.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.util.Constants;

import java.io.FileNotFoundException;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private ProfileContract.Presenter mPresenter;



    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mCoinCount;
    private TextView mArticleCount;
    private ImageView mImageViewPost;

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
        mImageViewPost = v.findViewById(R.id.imageView_prpfile_post_article);

        mUserImageView.setOnClickListener(this);
        mImageViewPost.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d(Constants.TAG, " onViewCreated mPresenter: " +mPresenter);

        setTypeFace();
        mPresenter.getUserImage(mContext);

    }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    private void setTypeFace() {

        mTypeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/GenJyuuGothicX-Bold.ttf");
        mUserName.setTypeface(mTypeface);
        mUserEmail.setTypeface(mTypeface);
        mCoinCount.setTypeface(mTypeface);
        mArticleCount.setTypeface(mTypeface);



    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.imageView_user){
            ((FoodieActivity)getActivity()).pickMyPicture();
        }
        if (view.getId() == R.id.imageView_prpfile_post_article) {
            ((FoodieActivity)getActivity()).transToPostArticle();

        }

    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        Log.d(Constants.TAG, " setPresenter mPresenter: " +mPresenter);

        mPresenter.start();
    }



    @Override
    public void showUserPicture(Bitmap bitmap) {
    }

    @Override
    public void showUserData(User user) {

        mUserName.setText(user.getName());
        mUserEmail.setText(user.getEmail());

        ContentResolver cr = mContext.getContentResolver();
        try {
            //由抽象資料接口轉換圖檔路徑為Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(user.getImage())));
            mUserImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(), e);
        }


    }
}
