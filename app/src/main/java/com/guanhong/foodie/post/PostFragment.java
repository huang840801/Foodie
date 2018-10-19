package com.guanhong.foodie.post;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.adapters.PostArticlePhotoAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Menu;
import com.guanhong.foodie.util.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostFragment extends Fragment implements PostContract.View, View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private PostContract.Presenter mPresenter;

    private Context mContext;
    private View mTopView;

    private EditText mEditTextRestaurantName;
    private TextView mTextViewRestaurantLocation;
    private ImageView mImageViewMarker;
    private ImageView mImageViewAddMenu;
    //    private ImageView mImageViewSubtractMenu;
    private EditText mEditTextMenu1;
    private EditText mEditTextPrice1;
    private EditText mEditTextMenu2;
    private EditText mEditTextPrice2;
    private EditText mEditTextMenu3;
    private EditText mEditTextPrice3;
    private RecyclerView mRecyclerViewPhoto;
    private ImageView mImageViewAddPhoto;
    private EditText mEditTextContent;
    private TextView mPostArticle;
    private RatingBar mRatingBar;

    private TextView mTextViewRestaurantName;
    private TextView mTextViewLocation;
    private TextView mTextViewMenu1;
    private TextView mTextViewMenu2;
    private TextView mTextViewMenu3;
    private TextView mTextViewPictures;
    private TextView mTextViewContent;
    private TextView mTextViewRating;

    private AVLoadingIndicatorView mAVLoadingIndicatorView;
    private View mLoadingBackground;


    private Typeface mTypeface;

    private ArrayList<String> mPictureList = new ArrayList<>();

    private int mStarCount = 0;
    private LatLng mLatLng;

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

        Log.d(Constants.TAG, " mPresenter = " + mPresenter);
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

        mContext = getContext();
        mTopView = rootView.findViewById(R.id.topView);

        mEditTextRestaurantName = rootView.findViewById(R.id.edittext_post_restaurant_name);
        mTextViewRestaurantLocation = rootView.findViewById(R.id.textview_post_restaurant_location);
        mImageViewMarker = rootView.findViewById(R.id.imageView_post_location);
        mImageViewAddMenu = rootView.findViewById(R.id.imageView_post_addMenu);
//        mImageViewSubtractMenu = rootView.findViewById(R.id.imageView_post_subtractMenu);
        mEditTextMenu1 = rootView.findViewById(R.id.edittext_post_menu1);
        mEditTextPrice1 = rootView.findViewById(R.id.edittext_post_price1);
        mEditTextMenu2 = rootView.findViewById(R.id.edittext_post_menu2);
        mEditTextPrice2 = rootView.findViewById(R.id.edittext_post_price2);
        mEditTextMenu3 = rootView.findViewById(R.id.edittext_post_menu3);
        mEditTextPrice3 = rootView.findViewById(R.id.edittext_post_price3);
        mRecyclerViewPhoto = rootView.findViewById(R.id.recyclerview_post_photo);
        mImageViewAddPhoto = rootView.findViewById(R.id.imageView_post_add_pictures);
        mEditTextContent = rootView.findViewById(R.id.edittext_post_content);

        mTextViewRestaurantName = rootView.findViewById(R.id.post_restaurant_name);
        mTextViewLocation = rootView.findViewById(R.id.post_restaurant_location);
        mTextViewMenu1 = rootView.findViewById(R.id.textView_menu1);
        mTextViewMenu2 = rootView.findViewById(R.id.textView_menu2);
        mTextViewMenu3 = rootView.findViewById(R.id.textView_menu3);
        mTextViewPictures = rootView.findViewById(R.id.textView_pictures);
        mTextViewContent = rootView.findViewById(R.id.textView_content);
        mTextViewRating = rootView.findViewById(R.id.textView_rating_bar);
        mPostArticle = rootView.findViewById(R.id.textview_post_post);

        mAVLoadingIndicatorView = rootView.findViewById(R.id.AVLoadingIndicatorView);
        mLoadingBackground = rootView.findViewById(R.id.loading_background);

        setTypeFace();

        mRecyclerViewPhoto = rootView.findViewById(R.id.recyclerview_post_photo);

        mRatingBar = rootView.findViewById(R.id.ratingBar2);

        return rootView;
    }

    private void setTypeFace() {
        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/GenJyuuGothicX-Bold.ttf");
        mTextViewRestaurantName.setTypeface(mTypeface);
        mTextViewLocation.setTypeface(mTypeface);
        mTextViewMenu1.setTypeface(mTypeface);
        mTextViewMenu2.setTypeface(mTypeface);
        mTextViewMenu3.setTypeface(mTypeface);
        mTextViewPictures.setTypeface(mTypeface);
        mTextViewContent.setTypeface(mTypeface);
        mTextViewRating.setTypeface(mTypeface);
        mPostArticle.setTypeface(mTypeface);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTopViewPadding();

        mAVLoadingIndicatorView.setVisibility(View.GONE);
        mLoadingBackground.setVisibility(View.GONE);
        mTextViewMenu2.setVisibility(View.GONE);
        mEditTextMenu2.setVisibility(View.GONE);
        mEditTextPrice2.setVisibility(View.GONE);
        mTextViewMenu3.setVisibility(View.GONE);
        mEditTextMenu3.setVisibility(View.GONE);
        mEditTextPrice3.setVisibility(View.GONE);

        mImageViewMarker.setOnClickListener(this);
        mImageViewAddMenu.setOnClickListener(this);
//        mImageViewSubtractMenu.setOnClickListener(this);
        mImageViewAddPhoto.setOnClickListener(this);
        mRecyclerViewPhoto.setOnClickListener(this);
        mPostArticle.setOnClickListener(this);
        mRatingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setTopViewPadding() {
        // Set the padding to match the Status Bar height
        mTopView.setPadding(0, getStatusBarHeight(), 0, 0);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void setTabLayoutVisibility(boolean visible) {
        ((FoodieActivity) getActivity()).setTabLayoutVisibility(visible);
    }

    @Override
    public void showAddress(final String addressLine, LatLng latLng) {

        mLatLng = latLng;

        Log.d(Constants.TAG, "   " + addressLine);
//        mTextViewRestaurantLocation.setText(addressLine);
        mTextViewRestaurantLocation.post(new Runnable() {
            @Override
            public void run() {
                mTextViewRestaurantLocation.setText(addressLine);
            }
        });

    }

    @Override
    public void showPictures(ArrayList<String> pictureArrayListExtra) {

        mPictureList = pictureArrayListExtra;

        mRecyclerViewPhoto.setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewPhoto.setHasFixedSize(true);
        mRecyclerViewPhoto.setAdapter(new PostArticlePhotoAdapter(pictureArrayListExtra, mPresenter));
//        mRecyclerViewPhoto.addItemDecoration(new SpaceItemDecoration(2));

        mRecyclerViewPhoto.smoothScrollToPosition(0);

    }

    @Override
    public void showNewPictures(ArrayList<String> newPictures) {
//        Log.d(Constants.TAG, "  showNewPictures " + newPictures);
        getArticleData(newPictures);
    }

    @Override
    public void addPictures() {
        ((FoodieActivity) getActivity()).pickMultiplePictures();

    }

    @Override
    public void transToProfile() {

        mAVLoadingIndicatorView.setVisibility(View.GONE);
        mLoadingBackground.setVisibility(View.GONE);
        ((FoodieActivity) getActivity()).transToProfile();

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_post_location) {
            ((FoodieActivity) getActivity()).transToPostChildMap();
        }
        if (view.getId() == R.id.imageView_post_addMenu) {
            addMenu();
        }
        if (view.getId() == R.id.recyclerview_post_photo) {
            addPictures();
        }
//        if (view.getId() == R.id.imageView_post_subtractMenu) {
//            subtractMenu();
//        }
        if (view.getId() == R.id.imageView_post_add_pictures) {
            addPictures();
        }
        if (view.getId() == R.id.textview_post_post) {
            checkInformation();
//            postImage();
        }

    }

    private void checkInformation() {
        Log.d(Constants.TAG, " namelength = " + mEditTextRestaurantName.getText().toString().length());

        if ("".equals(mEditTextRestaurantName.getText().toString())) {

            Toast.makeText(mContext, "店名必填!", Toast.LENGTH_SHORT).show();

        } else if ((mEditTextRestaurantName.getText().toString()).contains(" ")) {

            Toast.makeText(mContext, "店名不能有空白!", Toast.LENGTH_SHORT).show();

        } else if ("".equals(mTextViewRestaurantLocation.getText().toString())) {

            Toast.makeText(mContext, "位置必填!", Toast.LENGTH_SHORT).show();

        } else if ("".equals(mEditTextMenu1.getText().toString())) {

            Toast.makeText(mContext, "菜名必填!", Toast.LENGTH_SHORT).show();

        } else if ((mEditTextMenu1.getText().toString()).contains(" ")) {

            Toast.makeText(mContext, "菜單不能有空白!", Toast.LENGTH_SHORT).show();

        } else if ("".equals(mEditTextPrice1.getText().toString())) {

            Toast.makeText(mContext, "價格必填!", Toast.LENGTH_SHORT).show();

        } else if (mPictureList.size() == 0) {

            Toast.makeText(mContext, "至少要有一張照片!", Toast.LENGTH_SHORT).show();

        } else if ("".equals(mEditTextContent.getText().toString())) {

            Toast.makeText(mContext, "內容必填!", Toast.LENGTH_SHORT).show();

        } else if (mEditTextMenu2.getText().toString().length() > 0 || mEditTextPrice2.getText().toString().length() > 0) {

            if ("".equals(mEditTextMenu2.getText().toString())) {

                Toast.makeText(mContext, "菜單必填!", Toast.LENGTH_SHORT).show();

            } else if ((mEditTextMenu2.getText().toString()).contains(" ")) {

                Toast.makeText(mContext, "菜單不能有空白!", Toast.LENGTH_SHORT).show();

            } else if ("".equals(mEditTextPrice2.getText().toString())) {

                Toast.makeText(mContext, "價格必填!", Toast.LENGTH_SHORT).show();

            } else if (mEditTextMenu3.getText().toString().length() > 0 || mEditTextPrice3.getText().toString().length() > 0) {
                if ("".equals(mEditTextMenu3.getText().toString())) {

                    Toast.makeText(mContext, "菜單必填!", Toast.LENGTH_SHORT).show();

                } else if ((mEditTextMenu3.getText().toString()).contains(" ")) {

                    Toast.makeText(mContext, "菜單不能有空白!", Toast.LENGTH_SHORT).show();

                } else if ("".equals(mEditTextPrice3.getText().toString())) {

                    Toast.makeText(mContext, "價格必填!", Toast.LENGTH_SHORT).show();

                } else {
                    mAVLoadingIndicatorView.setVisibility(View.VISIBLE);
                    mLoadingBackground.setVisibility(View.VISIBLE);
                    postImage();
//                    Toast.makeText(mContext, "可以發文囉!", Toast.LENGTH_SHORT).show();
                }
            } else {
                mAVLoadingIndicatorView.setVisibility(View.VISIBLE);
                mLoadingBackground.setVisibility(View.VISIBLE);
                postImage();
//                Toast.makeText(mContext, "可以發文囉!", Toast.LENGTH_SHORT).show();
            }

        } else {
            mAVLoadingIndicatorView.setVisibility(View.VISIBLE);
            mLoadingBackground.setVisibility(View.VISIBLE);
            postImage();
//            Toast.makeText(mContext, "可以發文囉!", Toast.LENGTH_SHORT).show();
        }
//        String address = mTextViewRestaurantLocation.getText().toString();

//        String dishName1 = mEditTextMenu1.getText().toString();
//        String dishPrice1 = mEditTextPrice1.getText().toString();
//
//        String dishName2 = mEditTextMenu2.getText().toString();
//        String dishPrice2 = mEditTextPrice2.getText().toString();
//
//        String dishName3 = mEditTextMenu3.getText().toString();
//        String dishPrice3 = mEditTextPrice3.getText().toString();
//
//
//        String content = mEditTextContent.getText().toString();


    }


    private void postImage() {

        mPresenter.uploadImage(mPictureList);

    }

    private void subtractMenu() {
        if (mEditTextMenu3.getVisibility() == View.VISIBLE && mEditTextPrice3.getVisibility() == View.VISIBLE) {
            mEditTextMenu3.setVisibility(View.GONE);
            mEditTextPrice3.setVisibility(View.GONE);
        } else if (mEditTextMenu2.getVisibility() == View.VISIBLE && mEditTextPrice2.getVisibility() == View.VISIBLE) {
            mEditTextMenu2.setVisibility(View.GONE);
            mEditTextPrice2.setVisibility(View.GONE);
        } else {
            Toast.makeText(mContext, R.string.at_least_one, Toast.LENGTH_SHORT).show();
        }
    }

    private void addMenu() {
        if (mEditTextMenu2.getVisibility() == View.GONE && mEditTextPrice2.getVisibility() == View.GONE) {
            mEditTextMenu2.setVisibility(View.VISIBLE);
            mEditTextPrice2.setVisibility(View.VISIBLE);
        } else if (mEditTextMenu3.getVisibility() == View.GONE && mEditTextPrice3.getVisibility() == View.GONE) {
            mEditTextMenu3.setVisibility(View.VISIBLE);
            mEditTextPrice3.setVisibility(View.VISIBLE);
        } else if (mEditTextMenu3.getVisibility() == View.VISIBLE && mEditTextPrice3.getVisibility() == View.VISIBLE) {
//            Toast.makeText(mContext, R.string.too_much, Toast.LENGTH_SHORT).show();
        }
    }

    private void getArticleData(ArrayList<String> newPictures) {


        Log.d("UserManager", " email = " + UserManager.getInstance().getUserEmail());
        Log.d("UserManager", " id = " + UserManager.getInstance().getUserId());
        Log.d("UserManager", " image = " + UserManager.getInstance().getUserImage());
        Log.d("UserManager", " name = " + UserManager.getInstance().getUserName());

        Article article = new Article();
        Author author = new Author();
        ArrayList<Menu> menus = new ArrayList<>();
//        ArrayList<String> pictures = new ArrayList<>();
//        Menu menu = new Menu();

//        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
//        String uid = userData.getString("userUid", "");
//        String name = userData.getString("userName", "");
//        String image = userData.getString("userImage", "");

        author.setId(UserManager.getInstance().getUserId());
        author.setName(UserManager.getInstance().getUserName());
        author.setImage(UserManager.getInstance().getUserImage());

        Menu menu1 = new Menu();

        String dishName1 = mEditTextMenu1.getText().toString();
        String dishPrice1 = mEditTextPrice1.getText().toString();

        menu1.setDishName(dishName1);
        menu1.setDishPrice(dishPrice1);
        menus.add(menu1);

        if (mEditTextMenu2.getVisibility() == View.VISIBLE && mEditTextPrice2.getVisibility() == View.VISIBLE) {
            Menu menu2 = new Menu();
            String dishName2 = mEditTextMenu2.getText().toString();
            String dishPrice2 = mEditTextPrice2.getText().toString();

            menu2.setDishName(dishName2);
            menu2.setDishPrice(dishPrice2);
            menus.add(menu2);

        }
        if (mEditTextMenu3.getVisibility() == View.VISIBLE && mEditTextPrice3.getVisibility() == View.VISIBLE) {
            Menu menu3 = new Menu();
            String dishName3 = mEditTextMenu3.getText().toString();
            String dishPrice3 = mEditTextPrice3.getText().toString();

            menu3.setDishName(dishName3);
            menu3.setDishPrice(dishPrice3);
            menus.add(menu3);

        }


        String restaurantName = mEditTextRestaurantName.getText().toString();
        String address = mTextViewRestaurantLocation.getText().toString();
        String content = mEditTextContent.getText().toString();
        int starCount = mStarCount;

//        Geocoder geoCoder = new Geocoder(mContext, Locale.getDefault());
//        List<Address> addressLocation = null;
//        try {
//            addressLocation = geoCoder.getFromLocationName(address, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        double latitude = addressLocation.get(0).getLatitude();
//        double longitude = addressLocation.get(0).getLongitude();
        double latitude = mLatLng.latitude;
        double longitude = mLatLng.longitude;
//        String lat = (String.valueOf(latitude)).substring(0, Constants.LATLNG_SAVE_DIGITS);
//        String lng = (String.valueOf(longitude)).substring(0, Constants.LATLNG_SAVE_DIGITS);
        String lat = (String.valueOf(latitude));
        String lng = (String.valueOf(longitude));
        String latlng = (lat + "_" + lng).replace(".", "@");
//        latlng.replace(".", "");
        Log.d(Constants.TAG, "  hongtest post latitude = " + latitude);
        Log.d(Constants.TAG, "  hongtest post longitude = " + longitude);
        Log.d(Constants.TAG, "  hongtest post latlng = " + latlng);

        article.setAuthor(author);
        article.setLat_lng(latlng);
        article.setRestaurantName(restaurantName);
        article.setLocation(address);
        article.setMenus(menus);
//        article.setPictures(pictures);
        article.setContent(content);
        article.setStarCount(starCount);
        article.setLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
        article.setPictures(newPictures);

        mPresenter.postArticle(article);


    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        mStarCount = (int) v;
        Log.d(Constants.TAG, "  mStarCount = " + mStarCount);
    }

    public void getLocationData(final String addressLine, LatLng latLng) {
        Log.d("getLocationData", " PostFragment address = " + addressLine);
        Log.d("getLocationData", " PostFragment latlng = " + latLng);

//        mTextViewRestaurantLocation.post(new Runnable() {
//            @Override
//            public void run() {
//                mTextViewRestaurantLocation.setText(addressLine);
//            }
//        });
    }
}
