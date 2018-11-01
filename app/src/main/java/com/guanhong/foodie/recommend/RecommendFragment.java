package com.guanhong.foodie.recommend;

import static com.google.common.base.Preconditions.checkNotNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.MyService;
import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.RecommendPhotoAdapter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class RecommendFragment extends Fragment implements RecommendContract.View, Animation.AnimationListener {

    private RecommendContract.Presenter mPresenter;
    private Context mContext;

    private TextView mTitleFirst;
    private TextView mTitleSecond;
    private TextView mTitleThird;
    private TextView mTitleForth;

    private ShimmerTextView mRestaurantName;
    private TextView mLocation;
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private ImageView mStar4;
    private ImageView mStar5;
    private RecyclerView mRecyclerView;
    private ImageView mLabel;

//    private ShimmerTextView mShimmerTextView;

    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();

    private Timer mTimer;
    private int mCount = 0;

    private Animation mLabelAnimation;
    private Animation mFirstTextAnimation;
    private Animation mSecondTextAnimation;
    private Animation mThirdTextAnimation;
    private Animation mForthTextAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        mContext = getContext();

//        mShimmerTextView = v.findViewById(R.id.shimmer_tv);
        mTitleFirst = v.findViewById(R.id.textView_recommend_title_first);
        mTitleSecond = v.findViewById(R.id.textView_recommend_title_second);
        mTitleThird = v.findViewById(R.id.textView_recommend_title_third);
        mTitleForth = v.findViewById(R.id.textView_recommend_title_forth);
        mRestaurantName = v.findViewById(R.id.textView_restaurant_name);
        mLocation = v.findViewById(R.id.textView_location);
        mLabel = v.findViewById(R.id.imageView_recommend_label);
        mStar1 = v.findViewById(R.id.imageView_recommend_star1);
        mStar2 = v.findViewById(R.id.imageView_recommend_star2);
        mStar3 = v.findViewById(R.id.imageView_recommend_star3);
        mStar4 = v.findViewById(R.id.imageView_recommend_star4);
        mStar5 = v.findViewById(R.id.imageView_recommend_star5);
        mRecyclerView = v.findViewById(R.id.recommend_recyclerView);
        mTitleFirst.setVisibility(View.GONE);
        mTitleSecond.setVisibility(View.GONE);
        mTitleThird.setVisibility(View.GONE);
        mTitleForth.setVisibility(View.GONE);


        return v;
    }

    private void setShimmerText() {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000)
                .start(mRestaurantName);
    }

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAnimation();
        setShimmerText();

        mPresenter.start();

    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(Constants.TAG, "RecommendFragment mCount = " + mCount);

                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createRandomRestaurant();
                    }
                });

                mCount++;
//                if (mCount >= 5) {
//                    mTimer.cancel();
//                }
            }
        }, 1000, 1000 * 5);
    }


    private void startMyService() {

        Intent intent = new Intent(mContext, MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(intent);
        } else {
            mContext.startService(intent);
        }
    }


    @Override
    public void showAllRestaurantList(ArrayList<Restaurant> restaurantArrayList) {

        Log.d(Constants.TAG, "RecommendFragment " + restaurantArrayList.size());

        mRestaurantArrayList = restaurantArrayList;
        createRandomRestaurant();

    }

    public void createRandomRestaurant() {
        int random = (int) (Math.random() * mRestaurantArrayList.size());

        showTodayRecommendRestaurant(mRestaurantArrayList.get(random));
    }

    private void showTodayRecommendRestaurant(final Restaurant restaurant) {

        Log.d(Constants.TAG, "RecommendFragment " + restaurant.getRestaurantName());


        mRestaurantName.setText(restaurant.getRestaurantName());
        mLocation.setText(restaurant.getRestaurantLocation());
        if (restaurant.getStarCount() == 5) {
            mStar1.setImageResource(R.drawable.new_star_selected);
            mStar2.setImageResource(R.drawable.new_star_selected);
            mStar3.setImageResource(R.drawable.new_star_selected);
            mStar4.setImageResource(R.drawable.new_star_selected);
            mStar5.setImageResource(R.drawable.new_star_selected);

        } else if (restaurant.getStarCount() == 4) {
            mStar1.setImageResource(R.drawable.new_star_selected);
            mStar2.setImageResource(R.drawable.new_star_selected);
            mStar3.setImageResource(R.drawable.new_star_selected);
            mStar4.setImageResource(R.drawable.new_star_selected);
            mStar5.setImageResource(R.drawable.new_star_unselected);

        } else if (restaurant.getStarCount() == 3) {
            mStar1.setImageResource(R.drawable.new_star_selected);
            mStar2.setImageResource(R.drawable.new_star_selected);
            mStar3.setImageResource(R.drawable.new_star_selected);
            mStar4.setImageResource(R.drawable.new_star_unselected);
            mStar5.setImageResource(R.drawable.new_star_unselected);

        } else if (restaurant.getStarCount() == 2) {
            mStar1.setImageResource(R.drawable.new_star_selected);
            mStar2.setImageResource(R.drawable.new_star_selected);
            mStar3.setImageResource(R.drawable.new_star_unselected);
            mStar4.setImageResource(R.drawable.new_star_unselected);
            mStar5.setImageResource(R.drawable.new_star_unselected);

        } else if (restaurant.getStarCount() == 1) {
            mStar1.setImageResource(R.drawable.new_star_selected);
            mStar2.setImageResource(R.drawable.new_star_unselected);
            mStar3.setImageResource(R.drawable.new_star_unselected);
            mStar4.setImageResource(R.drawable.new_star_unselected);
            mStar5.setImageResource(R.drawable.new_star_unselected);

        } else if (restaurant.getStarCount() == 0) {
            mStar1.setImageResource(R.drawable.new_star_unselected);
            mStar2.setImageResource(R.drawable.new_star_unselected);
            mStar3.setImageResource(R.drawable.new_star_unselected);
            mStar4.setImageResource(R.drawable.new_star_unselected);
            mStar5.setImageResource(R.drawable.new_star_unselected);

        }


        mRecyclerView.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RecommendPhotoAdapter(restaurant.getRestaurantPictures()));

    }

    private void setAnimation() {

        mLabelAnimation = AnimationUtils.loadAnimation(mContext, R.anim.label);
        mLabel.setAnimation(mLabelAnimation);

        mFirstTextAnimation = AnimationUtils.loadAnimation(mContext, R.anim.text_alpha);
        mSecondTextAnimation = AnimationUtils.loadAnimation(mContext, R.anim.text_alpha);
        mThirdTextAnimation = AnimationUtils.loadAnimation(mContext, R.anim.text_alpha);
        mForthTextAnimation = AnimationUtils.loadAnimation(mContext, R.anim.text_alpha);


        mLabelAnimation.setAnimationListener(this);
        mFirstTextAnimation.setAnimationListener(this);
        mSecondTextAnimation.setAnimationListener(this);
        mThirdTextAnimation.setAnimationListener(this);

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == mLabelAnimation) {
            mTitleFirst.setVisibility(View.VISIBLE);
            mTitleFirst.setAnimation(mFirstTextAnimation);

        }
        if (animation == mFirstTextAnimation) {
            mTitleSecond.setVisibility(View.VISIBLE);
            mTitleSecond.setAnimation(mSecondTextAnimation);

        }
        if (animation == mSecondTextAnimation) {
            mTitleThird.setVisibility(View.VISIBLE);
            mTitleThird.setAnimation(mThirdTextAnimation);

        }
        if (animation == mThirdTextAnimation) {
            mTitleForth.setVisibility(View.VISIBLE);
            mTitleForth.setAnimation(mForthTextAnimation);

        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}