package com.guanhong.foodie.recommend;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.RecommendPhotoAdapter;
import com.guanhong.foodie.objects.Restaurant;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;

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

    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();

    private Animation mLabelAnimation;
    private Animation mFirstTextAnimation;
    private Animation mSecondTextAnimation;
    private Animation mThirdTextAnimation;
    private Animation mForthTextAnimation;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            mTitleFirst.setVisibility(View.GONE);
            mTitleSecond.setVisibility(View.GONE);
            mTitleThird.setVisibility(View.GONE);
            mTitleForth.setVisibility(View.GONE);
        } else {
            setAnimation();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        mContext = getContext();

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

    @Override
    public void showAllRestaurantList(ArrayList<Restaurant> restaurantArrayList) {

        mRestaurantArrayList = restaurantArrayList;
        createRandomRestaurant();
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

    public void createRandomRestaurant() {
        int random = (int) (Math.random() * mRestaurantArrayList.size());

        showTodayRecommendRestaurant(mRestaurantArrayList.get(random));
    }

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    private void showTodayRecommendRestaurant(final Restaurant restaurant) {

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

    private void setShimmerText() {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000)
                .start(mRestaurantName);
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
}