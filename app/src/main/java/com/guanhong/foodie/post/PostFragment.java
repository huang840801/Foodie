package com.guanhong.foodie.post;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.util.Constants;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostFragment extends Fragment implements PostContract.View, View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private PostContract.Presenter mPresenter;

    private EditText mEditTextRestaurantName;
    private TextView mTextViewRestaurantLocation;
    private ImageView mImageViewMarker;
    private ImageView mImageViewAddMenu;
    private ImageView mImageViewSubtractMenu;
    private EditText mEditTextMenu;
    private EditText mEditTextPrice;
    private RecyclerView mRecyclerViewPhoto;
    private ImageView mImageViewAddPhoto;
    private EditText mEditTextContent;
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private ImageView mStar4;
    private ImageView mStar5;
    private TextView mPostArticle;
    private RatingBar mRatingBar;
    private LinearLayout mLinearLayout;

   private int mStarCount = 0;
   private String mAddress;

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.hideTabLayout();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showTabLayout();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        mEditTextRestaurantName = rootView.findViewById(R.id.edittext_post_restaurant_name);
        mTextViewRestaurantLocation = rootView.findViewById(R.id.textview_post_restaurant_location);
        mImageViewMarker = rootView.findViewById(R.id.imageView_post_location);
        mImageViewAddMenu = rootView.findViewById(R.id.imageView_post_addMenu);
        mImageViewSubtractMenu = rootView.findViewById(R.id.imageView_post_subtractMenu);
        mEditTextMenu = rootView.findViewById(R.id.edittext_post_menu);
        mEditTextPrice = rootView.findViewById(R.id.edittext_post_price);
        mRecyclerViewPhoto = rootView.findViewById(R.id.recyclerview_post_photo);
        mImageViewAddPhoto = rootView.findViewById(R.id.imageView_post_add_pictures);
        mEditTextContent = rootView.findViewById(R.id.edittext_post_content);


        mRatingBar = rootView.findViewById(R.id.ratingBar2);
        mPostArticle = rootView.findViewById(R.id.textview_post_post);
        mLinearLayout = rootView.findViewById(R.id.linearLayout);


        mImageViewMarker.setOnClickListener(this);
        mImageViewAddMenu.setOnClickListener(this);
        mImageViewSubtractMenu.setOnClickListener(this);
        mImageViewAddPhoto.setOnClickListener(this);
        mPostArticle.setOnClickListener(this);
        mRatingBar.setOnRatingBarChangeListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            mAddress = bundle.getString("address");
//            Log.d(Constants.TAG, "  mAddress = " + mAddress);
//            Log.d(Constants.TAG, "  bundle != null " );
//        }
//        Log.d(Constants.TAG, "  bundle = null " );

    }

    @Override
    public void setTabLayoutVisibility(boolean visible) {
        ((FoodieActivity) getActivity()).setTabLayoutVisibility(visible);
    }

    @Override
    public void showAddress(final String addressLine) {

        Log.d(Constants.TAG, "   " + addressLine );
//        mTextViewRestaurantLocation.setText(addressLine);
   mTextViewRestaurantLocation.post(new Runnable() {
       @Override
       public void run() {
           mTextViewRestaurantLocation.setText(addressLine);
       }
   });

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_post_location){
            ((FoodieActivity)getActivity()).transToPostChildMap();

        } if (view.getId() == R.id.imageView_post_addMenu){


                mLinearLayout.addView(mEditTextMenu);
                mLinearLayout.addView(mEditTextPrice);

        } if (view.getId() == R.id.imageView_post_subtractMenu){
            mLinearLayout.removeView(mEditTextMenu);
            mLinearLayout.removeView(mEditTextPrice);

        } if (view.getId() == R.id.imageView_post_add_pictures){

        } if (view.getId() == R.id.textview_post_post){

        }

    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        mStarCount = (int) v;
        Log.d(Constants.TAG, "  mStarCount = " + mStarCount);
    }
}
