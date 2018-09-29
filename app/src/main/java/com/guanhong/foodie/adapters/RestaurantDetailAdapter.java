package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.restaurant.RestaurantContract;
import com.guanhong.foodie.util.Constants;
import com.guanhong.foodie.util.SpaceItemDecoration;
import com.rd.PageIndicatorView;

import java.util.ArrayList;

public class RestaurantDetailAdapter extends RecyclerView.Adapter {

    private RestaurantContract.Presenter mPresenter;

    private Restaurant mRestaurant;
    private ArrayList<Comment> mComments = new ArrayList<>();

    private Context mContext;

    public RestaurantDetailAdapter(Restaurant restaurant, RestaurantContract.Presenter presenter) {

        this.mRestaurant = restaurant;
        this.mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        if (viewType == Constants.VIEWTYPE_RESTAURANT_MAIN) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant_main, parent, false);
            return new RestaurantMainItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant_comments, parent, false);
            return new RestaurantCommentItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RestaurantMainItemViewHolder) {
            bindMainItem((RestaurantMainItemViewHolder) holder);
        } else if (holder instanceof RestaurantCommentItemViewHolder) {
            bindCommentItem((RestaurantCommentItemViewHolder) holder, position - 1);
        }

    }


    @Override
    public int getItemCount() {
        return 10 + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? Constants.VIEWTYPE_RESTAURANT_MAIN : Constants.VIEWTYPE_RESTAURANT_COMMENT;
    }

    public void updateRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;

        Log.d(Constants.TAG, " RestaurantDetailAdapter: " + mRestaurant.getRestaurantName());

        notifyDataSetChanged();

    }

    private void bindMainItem(final RestaurantMainItemViewHolder holder) {
        holder.getRecyclerViewPhotoGallery().setLayoutManager((new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.HORIZONTAL, false)));
//        holder.getRecyclerViewPhotoGallery().setOnFlingListener(null);
//        new LinearSnapHelper().attachToRecyclerView(holder.mRecyclerViewPhotoGallery);
        holder.getRecyclerViewPhotoGallery()
                .setAdapter(new RestaurantPhotoGalleryAdapter(mRestaurant.getRestaurantPictures()));
        new PagerSnapHelper().attachToRecyclerView(holder.mRecyclerViewPhotoGallery);
        final int size = mRestaurant.getRestaurantPictures().size();
        int remain = Integer.MAX_VALUE / 2 % size;
        holder.getRecyclerViewPhotoGallery().getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2 - remain);

        holder.getPageIndicatorView().setCount(size);
        holder.getRecyclerViewPhotoGallery().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                holder.getPageIndicatorView().setSelection(pastVisibleItems % size);
            }
        });

        holder.getRecyclerViewArticlePreview().setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.getRecyclerViewArticlePreview().setHasFixedSize(true);
        holder.getRecyclerViewArticlePreview().addItemDecoration(new SpaceItemDecoration(2));
        holder.getRecyclerViewArticlePreview().setAdapter(new RestaurantArticlePreviewAdapter());


        holder.getRestaurantName().setText(mRestaurant.getRestaurantName());
        holder.getRestaurantPosition().setText(mRestaurant.getRestaurantLocation());
        if (mRestaurant.getStarCount() == 5) {
            holder.getStar1().setImageResource(R.drawable.star_selected);
            holder.getStar2().setImageResource(R.drawable.star_selected);
            holder.getStar3().setImageResource(R.drawable.star_selected);
            holder.getStar4().setImageResource(R.drawable.star_selected);
            holder.getStar5().setImageResource(R.drawable.star_selected);
        } else if (mRestaurant.getStarCount() == 4) {
            holder.getStar1().setImageResource(R.drawable.star_selected);
            holder.getStar2().setImageResource(R.drawable.star_selected);
            holder.getStar3().setImageResource(R.drawable.star_selected);
            holder.getStar4().setImageResource(R.drawable.star_selected);
            holder.getStar5().setImageResource(R.drawable.star_unselected);
        } else if (mRestaurant.getStarCount() == 3) {
            holder.getStar1().setImageResource(R.drawable.star_selected);
            holder.getStar2().setImageResource(R.drawable.star_selected);
            holder.getStar3().setImageResource(R.drawable.star_selected);
            holder.getStar4().setImageResource(R.drawable.star_unselected);
            holder.getStar5().setImageResource(R.drawable.star_unselected);
        } else if (mRestaurant.getStarCount() == 2) {
            holder.getStar1().setImageResource(R.drawable.star_selected);
            holder.getStar2().setImageResource(R.drawable.star_selected);
            holder.getStar3().setImageResource(R.drawable.star_unselected);
            holder.getStar4().setImageResource(R.drawable.star_unselected);
            holder.getStar5().setImageResource(R.drawable.star_unselected);
        } else if (mRestaurant.getStarCount() == 1) {
            holder.getStar1().setImageResource(R.drawable.star_selected);
            holder.getStar2().setImageResource(R.drawable.star_unselected);
            holder.getStar3().setImageResource(R.drawable.star_unselected);
            holder.getStar4().setImageResource(R.drawable.star_unselected);
            holder.getStar5().setImageResource(R.drawable.star_unselected);
        }

    }

    private class RestaurantMainItemViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerViewPhotoGallery;
        private TextView mRestaurantName;
        private ImageView mStar1;
        private ImageView mStar2;
        private ImageView mStar3;
        private ImageView mStar4;
        private ImageView mStar5;
        private TextView mRestaurantPosition;
        private RecyclerView mRecyclerViewArticlePreview;
        private EditText mEditTextComment;
        private ImageView mButtonSend;
        private PageIndicatorView pageIndicatorView;

        public RestaurantMainItemViewHolder(View itemView) {
            super(itemView);
            mRecyclerViewPhotoGallery = itemView.findViewById(R.id.recyclerview_photo);
            mRestaurantName = itemView.findViewById(R.id.textView_restaurant_name);
            mStar1 = itemView.findViewById(R.id.imageView_star1);
            mStar2 = itemView.findViewById(R.id.imageView_star2);
            mStar3 = itemView.findViewById(R.id.imageView_star3);
            mStar4 = itemView.findViewById(R.id.imageView_star4);
            mStar5 = itemView.findViewById(R.id.imageView_star5);
            mRestaurantPosition = itemView.findViewById(R.id.textView_location);
            mRecyclerViewArticlePreview = itemView.findViewById(R.id.recyclerView_article_preview);
            mEditTextComment = itemView.findViewById(R.id.editText_comments);
            mButtonSend = itemView.findViewById(R.id.imageView_send);
            pageIndicatorView = itemView.findViewById(R.id.indicator);

        }

        public RecyclerView getRecyclerViewPhotoGallery() {
            return mRecyclerViewPhotoGallery;
        }

        public TextView getRestaurantName() {
            return mRestaurantName;
        }

        public ImageView getStar1() {
            return mStar1;
        }

        public ImageView getStar2() {
            return mStar2;
        }

        public ImageView getStar3() {
            return mStar3;
        }

        public ImageView getStar4() {
            return mStar4;
        }

        public ImageView getStar5() {
            return mStar5;
        }

        public TextView getRestaurantPosition() {
            return mRestaurantPosition;
        }

        public RecyclerView getRecyclerViewArticlePreview() {
            return mRecyclerViewArticlePreview;
        }

        public EditText getEditTextComment() {
            return mEditTextComment;
        }

        public ImageView getButtonSend() {
            return mButtonSend;
        }

        public PageIndicatorView getPageIndicatorView() {
            return pageIndicatorView;
        }
    }

    private void bindCommentItem(RestaurantCommentItemViewHolder holder, int i) {

        holder.getTextAuthorName().setText("Stin");
        holder.mTextCommentContent.setText("blalalalalalalalalalalalalalalalalalalalala");
    }

    private class RestaurantCommentItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageAuthorImage;
        private TextView mTextAuthorName;
        private TextView mTextCommentContent;


        public RestaurantCommentItemViewHolder(View itemView) {
            super(itemView);

            mImageAuthorImage = (ImageView) itemView.findViewById(R.id.imageView_authorImage);
            mTextAuthorName = (TextView) itemView.findViewById(R.id.textView_authorName);
            mTextCommentContent = (TextView) itemView.findViewById(R.id.textView_comment_content);
        }

        public ImageView getImageAuthorImage() {
            return mImageAuthorImage;
        }

        public TextView getTextAuthorName() {
            return mTextAuthorName;
        }

        public TextView getTextCommentContent() {
            return mTextCommentContent;
        }
    }
}
