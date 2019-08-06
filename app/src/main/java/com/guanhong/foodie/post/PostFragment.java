package com.guanhong.foodie.post;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.UserManager;
import com.guanhong.foodie.mainActivity.FoodieActivity;
import com.guanhong.foodie.adapters.PostArticlePhotoAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Menu;
import com.guanhong.foodie.util.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class PostFragment extends Fragment implements PostContract.View, View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private PostContract.Presenter mPresenter;

    private Context mContext;
    private View mTopView;

    private ConstraintLayout mConstraintLayout;
    private EditText mEditTextRestaurantName;
    private TextView mTextViewRestaurantLocation;
    private ImageView mImageViewMarker;
    private ImageView mImageViewAddMenu;
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

    private AVLoadingIndicatorView mAvLoadingIndicatorView;
    private View mLoadingBackground;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        mContext = getContext();
        mTopView = rootView.findViewById(R.id.topView);
        mConstraintLayout = rootView.findViewById(R.id.constraint_parent);
        mEditTextRestaurantName = rootView.findViewById(R.id.edittext_post_restaurant_name);
        mTextViewRestaurantLocation = rootView.findViewById(R.id.textview_post_restaurant_location);
        mImageViewMarker = rootView.findViewById(R.id.imageView_post_location);
        mImageViewAddMenu = rootView.findViewById(R.id.imageView_post_addMenu);
        mEditTextMenu1 = rootView.findViewById(R.id.edittext_post_menu1);
        mEditTextPrice1 = rootView.findViewById(R.id.edittext_post_price1);
        mEditTextMenu2 = rootView.findViewById(R.id.edittext_post_menu2);
        mEditTextPrice2 = rootView.findViewById(R.id.edittext_post_price2);
        mEditTextMenu3 = rootView.findViewById(R.id.edittext_post_menu3);
        mEditTextPrice3 = rootView.findViewById(R.id.edittext_post_price3);
        mRecyclerViewPhoto = rootView.findViewById(R.id.recyclerview_post_photo);
        mImageViewAddPhoto = rootView.findViewById(R.id.imageView_post_add_pictures);
        mEditTextContent = rootView.findViewById(R.id.edittext_post_content);
        mPostArticle = rootView.findViewById(R.id.textview_post_post);
        mAvLoadingIndicatorView = rootView.findViewById(R.id.AVLoadingIndicatorView);
        mLoadingBackground = rootView.findViewById(R.id.loading_background);
        mRecyclerViewPhoto = rootView.findViewById(R.id.recyclerview_post_photo);
        mRatingBar = rootView.findViewById(R.id.ratingBar2);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((FoodieActivity) getActivity()).checkRestaurantExists();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTopViewPadding();

        mAvLoadingIndicatorView.setVisibility(View.GONE);
        mLoadingBackground.setVisibility(View.GONE);
        mEditTextMenu2.setVisibility(View.GONE);
        mEditTextPrice2.setVisibility(View.GONE);
        mEditTextMenu3.setVisibility(View.GONE);
        mEditTextPrice3.setVisibility(View.GONE);

        mConstraintLayout.setOnClickListener(this);
        mImageViewMarker.setOnClickListener(this);
        mImageViewAddMenu.setOnClickListener(this);
        mImageViewAddPhoto.setOnClickListener(this);
        mRecyclerViewPhoto.setOnClickListener(this);
        mPostArticle.setOnClickListener(this);
        mTextViewRestaurantLocation.setOnClickListener(this);
        mRatingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    public void showAddress(final String addressLine, LatLng latLng) {

        mLatLng = latLng;

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
        mRecyclerViewPhoto.smoothScrollToPosition(0);
    }

    @Override
    public void showNewPictures(ArrayList<String> newPictures) {
        getArticleData(newPictures);
    }

    @Override
    public void addPictures() {
        ((FoodieActivity) getActivity()).pickMultiplePictures();
    }

    @Override
    public void transToMap() {
        mAvLoadingIndicatorView.setVisibility(View.GONE);
        mLoadingBackground.setVisibility(View.GONE);
        ((FoodieActivity) getActivity()).transToMap();
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(mContext, "照片無法讀取!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView_post_location || view.getId() == R.id.textview_post_restaurant_location) {
            ((FoodieActivity) getActivity()).transToPostChildMap();
        }
        if (view.getId() == R.id.imageView_post_addMenu) {
            addMenu();
        }
        if (view.getId() == R.id.recyclerview_post_photo) {
            addPictures();
        }
        if (view.getId() == R.id.imageView_post_add_pictures) {
            addPictures();
        }
        if (view.getId() == R.id.textview_post_post) {
            checkInformation();
        }
        if (view.getId() == R.id.constraint_parent) {

            InputMethodManager imManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        mStarCount = (int) v;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) result = mContext.getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    private void setTopViewPadding() {
        mTopView.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    public void checkInformation() {

        if ("".equals(mEditTextRestaurantName.getText().toString())) {

            Toast.makeText(mContext, R.string.restaurant_name_cannot_be_empty, Toast.LENGTH_SHORT).show();

        } else if ((mEditTextRestaurantName.getText().toString()).contains(" ")) {

            Toast.makeText(mContext, R.string.restaurant_name_cannot_contain_empty, Toast.LENGTH_SHORT).show();

        } else if ("".equals(mTextViewRestaurantLocation.getText().toString())) {

            Toast.makeText(mContext, R.string.location_cannot_be_empty, Toast.LENGTH_SHORT).show();

        } else if ("".equals(mEditTextMenu1.getText().toString())) {

            Toast.makeText(mContext, R.string.dish_name_cannot_be_empty, Toast.LENGTH_SHORT).show();

        } else if ((mEditTextMenu1.getText().toString()).contains(" ")) {

            Toast.makeText(mContext, R.string.dish_name_cannot_contain_empty, Toast.LENGTH_SHORT).show();

        } else if ("".equals(mEditTextPrice1.getText().toString())) {

            Toast.makeText(mContext, R.string.dish_price_cannot_be_empty, Toast.LENGTH_SHORT).show();

        } else if (mPictureList.size() == 0) {

            Toast.makeText(mContext, R.string.at_least_one_photo, Toast.LENGTH_SHORT).show();

        } else if ("".equals(mEditTextContent.getText().toString())) {

            Toast.makeText(mContext, R.string.content_cannot_be_empty, Toast.LENGTH_SHORT).show();

        } else if (mEditTextMenu2.getText().toString().length() > 0 || mEditTextPrice2.getText().toString().length() > 0) {

            if ("".equals(mEditTextMenu2.getText().toString())) {

                Toast.makeText(mContext, R.string.dish_name_cannot_be_empty, Toast.LENGTH_SHORT).show();

            } else if ((mEditTextMenu2.getText().toString()).contains(" ")) {

                Toast.makeText(mContext, R.string.dish_name_cannot_contain_empty, Toast.LENGTH_SHORT).show();

            } else if ("".equals(mEditTextPrice2.getText().toString())) {

                Toast.makeText(mContext, R.string.dish_price_cannot_be_empty, Toast.LENGTH_SHORT).show();

            } else if (mEditTextMenu3.getText().toString().length() > 0 || mEditTextPrice3.getText().toString().length() > 0) {
                if ("".equals(mEditTextMenu3.getText().toString())) {

                    Toast.makeText(mContext, R.string.dish_name_cannot_be_empty, Toast.LENGTH_SHORT).show();

                } else if ((mEditTextMenu3.getText().toString()).contains(" ")) {

                    Toast.makeText(mContext, R.string.dish_name_cannot_contain_empty, Toast.LENGTH_SHORT).show();

                } else if ("".equals(mEditTextPrice3.getText().toString())) {

                    Toast.makeText(mContext, R.string.dish_price_cannot_be_empty, Toast.LENGTH_SHORT).show();

                } else {
                    mAvLoadingIndicatorView.setVisibility(View.VISIBLE);
                    mLoadingBackground.setVisibility(View.VISIBLE);
                    postImage();
                }
            } else {
                mAvLoadingIndicatorView.setVisibility(View.VISIBLE);
                mLoadingBackground.setVisibility(View.VISIBLE);
                postImage();
            }

        } else {
            mAvLoadingIndicatorView.setVisibility(View.VISIBLE);
            mLoadingBackground.setVisibility(View.VISIBLE);
            postImage();
        }
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
        }
    }

    private void getArticleData(ArrayList<String> newPictures) {

        final Article article = new Article();
        final Author author = new Author();
        final ArrayList<Menu> menus = new ArrayList<>();
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

        final String restaurantName = mEditTextRestaurantName.getText().toString();
        final String address = mTextViewRestaurantLocation.getText().toString();
        final String content = mEditTextContent.getText().toString();
        final int starCount = mStarCount;

        double latitude = mLatLng.latitude;
        double longitude = mLatLng.longitude;

        String lat = (String.valueOf(latitude));
        String lng = (String.valueOf(longitude));
        String latlng = (lat + "_" + lng).replace(".", "@");

        article.setAuthor(author);
        article.setLat_lng(latlng);
        article.setRestaurantName(restaurantName);
        article.setLocation(address);
        article.setMenus(menus);
        article.setContent(content);
        article.setStarCount(starCount);
        article.setLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
        article.setPictures(newPictures);
        article.setCreatedTime(System.currentTimeMillis() + "");
        mPresenter.postArticle(article);
    }
}
