package com.guanhong.foodie.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.custom.CircleCornerForm;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.search.SearchContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchRestaurantAdapter extends RecyclerView.Adapter {

    private ArrayList<Restaurant> mRestaurantArrayList;
    private Context mContext;
    private SearchContract.Presenter mPresenter;


    public SearchRestaurantAdapter(SearchContract.Presenter presenter, ArrayList<Restaurant> restaurantArrayList) {
        mRestaurantArrayList = restaurantArrayList;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_articles, parent, false);
        return new SearchArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchArticleHolder) {
            bindMainItem((SearchArticleHolder) holder, position);
        }
    }

    private void bindMainItem(SearchArticleHolder holder, int position) {

        holder.mTextViewRestaurantName.setText(mRestaurantArrayList.get(position).getRestaurantName());
        holder.mTextViewLocation.setText(mRestaurantArrayList.get(position).getRestaurantLocation());

        if (mRestaurantArrayList.get(position).getRestaurantPictures().size() > 0) {
            Picasso.get()
                    .load(mRestaurantArrayList.get(position).getRestaurantPictures().get(0))
                    .fit()
                    .transform(new CircleCornerForm())
                    .placeholder(R.drawable.animated_rotate_drawable)
                    .error(R.drawable.photo_error)
                    .into(holder.mImageView);
        }

        if (mRestaurantArrayList.get(position).getStarCount() == 5) {
            holder.mStar1.setImageResource(R.drawable.new_star_selected);
            holder.mStar2.setImageResource(R.drawable.new_star_selected);
            holder.mStar3.setImageResource(R.drawable.new_star_selected);
            holder.mStar4.setImageResource(R.drawable.new_star_selected);
            holder.mStar5.setImageResource(R.drawable.new_star_selected);
        }
        if (mRestaurantArrayList.get(position).getStarCount() == 4) {
            holder.mStar1.setImageResource(R.drawable.new_star_selected);
            holder.mStar2.setImageResource(R.drawable.new_star_selected);
            holder.mStar3.setImageResource(R.drawable.new_star_selected);
            holder.mStar4.setImageResource(R.drawable.new_star_selected);
            holder.mStar5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mRestaurantArrayList.get(position).getStarCount() == 3) {
            holder.mStar1.setImageResource(R.drawable.new_star_selected);
            holder.mStar2.setImageResource(R.drawable.new_star_selected);
            holder.mStar3.setImageResource(R.drawable.new_star_selected);
            holder.mStar4.setImageResource(R.drawable.new_star_unselected);
            holder.mStar5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mRestaurantArrayList.get(position).getStarCount() == 2) {
            holder.mStar1.setImageResource(R.drawable.new_star_selected);
            holder.mStar2.setImageResource(R.drawable.new_star_selected);
            holder.mStar3.setImageResource(R.drawable.new_star_unselected);
            holder.mStar4.setImageResource(R.drawable.new_star_unselected);
            holder.mStar5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mRestaurantArrayList.get(position).getStarCount() == 1) {
            holder.mStar1.setImageResource(R.drawable.new_star_selected);
            holder.mStar2.setImageResource(R.drawable.new_star_unselected);
            holder.mStar3.setImageResource(R.drawable.new_star_unselected);
            holder.mStar4.setImageResource(R.drawable.new_star_unselected);
            holder.mStar5.setImageResource(R.drawable.new_star_unselected);
        }
        if (mRestaurantArrayList.get(position).getStarCount() == 0) {
            holder.mStar1.setImageResource(R.drawable.new_star_unselected);
            holder.mStar2.setImageResource(R.drawable.new_star_unselected);
            holder.mStar3.setImageResource(R.drawable.new_star_unselected);
            holder.mStar4.setImageResource(R.drawable.new_star_unselected);
            holder.mStar5.setImageResource(R.drawable.new_star_unselected);
        }
    }

    @Override
    public int getItemCount() {
//        return 0;
        Log.d("Huang ", "SearchRestaurantAdapter " + mRestaurantArrayList.size());

        return mRestaurantArrayList.size();
    }

    private class SearchArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mTextViewRestaurantName;
        private TextView mTextViewLocation;
        private ImageView mStar1;
        private ImageView mStar2;
        private ImageView mStar3;
        private ImageView mStar4;
        private ImageView mStar5;


        public SearchArticleHolder(View view) {
            super(view);

            mImageView = view.findViewById(R.id.imageView_search_article_photo);
            mTextViewRestaurantName = view.findViewById(R.id.textView_search_article_restaurantName);
            mTextViewLocation = view.findViewById(R.id.textView_search_article_location);
            mStar1 = view.findViewById(R.id.imageView_search_star1);
            mStar2 = view.findViewById(R.id.imageView_search_star2);
            mStar3 = view.findViewById(R.id.imageView_search_star3);
            mStar4 = view.findViewById(R.id.imageView_search_star4);
            mStar5 = view.findViewById(R.id.imageView_search_star5);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mPresenter.transToRestaurant(mRestaurantArrayList.get(getAdapterPosition()));
        }
    }
}
