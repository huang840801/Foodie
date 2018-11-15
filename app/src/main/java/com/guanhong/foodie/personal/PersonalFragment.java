package com.guanhong.foodie.personal;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.adapters.PersonalPhotoAdapter;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.Constants;
import com.rd.PageIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PersonalFragment extends Fragment implements PersonalContract.View {

    private PersonalContract.Presenter mPresenter;
    private Context mContext;

    private TextView mTextViewRestaurantName;
    private TextView mTextViewRestaurantLocation;
    private TextView mTextViewMenu1;
    private TextView mTextViewPrice1;
    private TextView mTextViewMenu2;
    private TextView mTextViewPrice2;
    private TextView mTextViewMenu3;
    private TextView mTextViewPrice3;
    private RecyclerView mRecyclerViewPhoto;
    private TextView mTextViewContent;
    private ImageView mImageViewStar1;
    private ImageView mImageViewStar2;
    private ImageView mImageViewStar3;
    private ImageView mImageViewStar4;
    private ImageView mImageViewStar5;

    private TextView mAuthor;
    private TextView mAuthorName;
    private TextView mCreatedTime;

    private PageIndicatorView mPageIndicatorView;

    private TextView mTitleMenu1;
    private TextView mTitleContent;

    private Typeface mTypeface;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    public PersonalFragment() {}

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_personal_article, container, false);

        mTextViewRestaurantName = view.findViewById(R.id.textView_personal_restaurant_name);
        mTextViewRestaurantLocation = view.findViewById(R.id.textview_personal_restaurant_location);
        mTextViewMenu1 = view.findViewById(R.id.textView_personal_menu1);
        mTextViewPrice1 = view.findViewById(R.id.textView_personal_price1);
        mTextViewMenu2 = view.findViewById(R.id.textView_personal_menu2);
        mTextViewPrice2 = view.findViewById(R.id.textView_personal_price2);
        mTextViewMenu3 = view.findViewById(R.id.textView_personal_menu3);
        mTextViewPrice3 = view.findViewById(R.id.textView_personal_price3);
        mRecyclerViewPhoto = view.findViewById(R.id.recyclerview_personal_photo);
        mTextViewContent = view.findViewById(R.id.textView_personal_content);
        mImageViewStar1 = view.findViewById(R.id.imageView_star1);
        mImageViewStar2 = view.findViewById(R.id.imageView_star2);
        mImageViewStar3 = view.findViewById(R.id.imageView_star3);
        mImageViewStar4 = view.findViewById(R.id.imageView_star4);
        mImageViewStar5 = view.findViewById(R.id.imageView_star5);
        mImageViewStar5 = view.findViewById(R.id.imageView_star5);
        mAuthor = view.findViewById(R.id.textView_personal_author);
        mAuthorName = view.findViewById(R.id.textView_personal_author_name);
        mCreatedTime = view.findViewById(R.id.textView_personal_createdTime);
        mPageIndicatorView = view.findViewById(R.id.personal_indicator);
        mTitleMenu1 = view.findViewById(R.id.textView_menu1);
        mTitleContent = view.findViewById(R.id.textView_content);

        return view;
    }

    @Override
    public void setPresenter(PersonalContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fragmentflow", "   PersonalFragment onDestroy ");

        mPresenter.checkRestaurantExists();
//        ((FoodieActivity) getActivity()).checkRestaurantExists();
    }

    private void setTypeFace() {
        mTypeface = Typeface.createFromAsset(mContext.getAssets(), Constants.TYPAFACE);

        mTextViewRestaurantName.setTypeface(mTypeface);
        mTextViewRestaurantLocation.setTypeface(mTypeface);
        mTextViewMenu1.setTypeface(mTypeface);
        mTextViewPrice1.setTypeface(mTypeface);
        mTextViewMenu2.setTypeface(mTypeface);
        mTextViewPrice2.setTypeface(mTypeface);
        mTextViewMenu3.setTypeface(mTypeface);
        mTextViewPrice3.setTypeface(mTypeface);
        mTextViewContent.setTypeface(mTypeface);
        mTitleMenu1.setTypeface(mTypeface);
        mTitleContent.setTypeface(mTypeface);
        mAuthor.setTypeface(mTypeface);
        mAuthorName.setTypeface(mTypeface);
    }

    @Override
    public void showArticleUi(Article article) {

        mTextViewRestaurantName.setText(article.getRestaurantName());
        mTextViewRestaurantLocation.setText(article.getLocation());
        mAuthorName.setText(article.getAuthor().getName());

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        long lcc = Long.valueOf(article.getCreatedTime());
        String time = formatter.format(new Date(lcc));
        mCreatedTime.setText(time);


        if (article.getMenus().size() == 1) {

            mTextViewMenu1.setText(article.getMenus().get(0).getDishName());
            mTextViewPrice1.setText(article.getMenus().get(0).getDishPrice());

            mTextViewMenu2.setVisibility(View.GONE);
            mTextViewPrice2.setVisibility(View.GONE);
            mTextViewMenu3.setVisibility(View.GONE);
            mTextViewPrice3.setVisibility(View.GONE);

        } else if (article.getMenus().size() == 2) {

            mTextViewMenu1.setText(article.getMenus().get(0).getDishName());
            mTextViewPrice1.setText(article.getMenus().get(0).getDishPrice());
            mTextViewMenu2.setText(article.getMenus().get(1).getDishName());
            mTextViewPrice2.setText(article.getMenus().get(1).getDishPrice());

            mTextViewMenu3.setVisibility(View.GONE);
            mTextViewPrice3.setVisibility(View.GONE);
        } else if (article.getMenus().size() == 3) {
            mTextViewMenu1.setText(article.getMenus().get(0).getDishName());
            mTextViewPrice1.setText(article.getMenus().get(0).getDishPrice());
            mTextViewMenu2.setText(article.getMenus().get(1).getDishName());
            mTextViewPrice2.setText(article.getMenus().get(1).getDishPrice());
            mTextViewMenu3.setText(article.getMenus().get(2).getDishName());
            mTextViewPrice3.setText(article.getMenus().get(2).getDishPrice());
        }

        setPhotoRecyclerView(article);

        mTextViewContent.setText(article.getContent());

        if (article.getStarCount() == 5) {
            mImageViewStar1.setImageResource(R.drawable.new_star_selected);
            mImageViewStar2.setImageResource(R.drawable.new_star_selected);
            mImageViewStar3.setImageResource(R.drawable.new_star_selected);
            mImageViewStar4.setImageResource(R.drawable.new_star_selected);
            mImageViewStar5.setImageResource(R.drawable.new_star_selected);
        } else if (article.getStarCount() == 4) {
            mImageViewStar1.setImageResource(R.drawable.new_star_selected);
            mImageViewStar2.setImageResource(R.drawable.new_star_selected);
            mImageViewStar3.setImageResource(R.drawable.new_star_selected);
            mImageViewStar4.setImageResource(R.drawable.new_star_selected);
            mImageViewStar5.setImageResource(R.drawable.new_star_unselected);
        } else if (article.getStarCount() == 3) {
            mImageViewStar1.setImageResource(R.drawable.new_star_selected);
            mImageViewStar2.setImageResource(R.drawable.new_star_selected);
            mImageViewStar3.setImageResource(R.drawable.new_star_selected);
            mImageViewStar4.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar5.setImageResource(R.drawable.new_star_unselected);
        } else if (article.getStarCount() == 2) {
            mImageViewStar1.setImageResource(R.drawable.new_star_selected);
            mImageViewStar2.setImageResource(R.drawable.new_star_selected);
            mImageViewStar3.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar4.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar5.setImageResource(R.drawable.new_star_unselected);

        } else if (article.getStarCount() == 1) {
            mImageViewStar1.setImageResource(R.drawable.new_star_selected);
            mImageViewStar2.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar3.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar4.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar5.setImageResource(R.drawable.new_star_unselected);
        } else if (article.getStarCount() == 0) {
            mImageViewStar1.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar2.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar3.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar4.setImageResource(R.drawable.new_star_unselected);
            mImageViewStar5.setImageResource(R.drawable.new_star_unselected);
        }
    }

    private void setPhotoRecyclerView(Article article) {

        mRecyclerViewPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        mRecyclerViewPhoto.setAdapter(new PersonalPhotoAdapter(article.getPictures()));
        new PagerSnapHelper().attachToRecyclerView(mRecyclerViewPhoto);
        final int size = article.getPictures().size();
        int remain = Integer.MAX_VALUE / 2 % size;
        mRecyclerViewPhoto.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2 - remain);

        mPageIndicatorView.setCount(size);
        mRecyclerViewPhoto.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                mPageIndicatorView.setSelection(pastVisibleItems % size);
            }
        });
    }

}
