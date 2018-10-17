package com.guanhong.foodie.recommend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.MyService;
import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.RecommendPhotoAdapter;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.util.Constants;
import com.guanhong.foodie.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecommendFragment extends Fragment implements RecommendContract.View {

    private RecommendContract.Presenter mPresenter;
    private Context mContext;

    private TextView mTitle;
    private TextView mRestaurantName;
    private TextView mLocation;
    private TextView mPhoto;
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private ImageView mStar4;
    private ImageView mStar5;
    private RecyclerView mRecyclerView;

    private Typeface mTypeface;
    private ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<>();

    private Timer mTimer ;
    private int mCount =  0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        mContext = getContext();

        mTitle = v.findViewById(R.id.textView_recommend_title);
        mRestaurantName = v.findViewById(R.id.textView_restaurant_name);
        mLocation = v.findViewById(R.id.textView_location);
        mPhoto = v.findViewById(R.id.textView_photo);
        mStar1 = v.findViewById(R.id.imageView_recommend_star1);
        mStar2 = v.findViewById(R.id.imageView_recommend_star2);
        mStar3 = v.findViewById(R.id.imageView_recommend_star3);
        mStar4 = v.findViewById(R.id.imageView_recommend_star4);
        mStar5 = v.findViewById(R.id.imageView_recommend_star5);
        mRecyclerView = v.findViewById(R.id.recommend_recyclerView);

        return v;
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
        setTypeFace();
        mPresenter.start();

//        mHandler = new Handler()
//        startMyService();
//        startTimer();

    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(Constants.TAG, "RecommendFragment mCount = " +mCount);

                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createRandomRestaurant();
                    }
                });

//                createRandomRestaurant();
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

    private void setTypeFace() {
        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/GenJyuuGothicX-Bold.ttf");
        mTitle.setTypeface(mTypeface);
        mRestaurantName.setTypeface(mTypeface);
        mLocation.setTypeface(mTypeface);
        mPhoto.setTypeface(mTypeface);
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
                    mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
                }





}