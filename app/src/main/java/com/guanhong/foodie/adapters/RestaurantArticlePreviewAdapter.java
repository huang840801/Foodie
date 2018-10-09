package com.guanhong.foodie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanhong.foodie.R;
import com.guanhong.foodie.activities.FoodieActivity;
import com.guanhong.foodie.custom.CircleCornerForm;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.restaurant.RestaurantContract;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantArticlePreviewAdapter extends RecyclerView.Adapter {

    private RestaurantContract.Presenter mPresenter;

    private Context mContext;
    private ArrayList<Article> mArticleArrayList;

    public RestaurantArticlePreviewAdapter(ArrayList<Article> articleArrayList, RestaurantContract.Presenter presenter) {
        mArticleArrayList = articleArrayList;
        mPresenter = presenter;
        Log.d(Constants.TAG, " RestaurantArticlePreviewAdapter presenter = " + presenter);
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.size());
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.get(0).getPictures());
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.get(0).getMenus().size());
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.get(0).getMenus().get(0).getDishName());
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.get(0).getMenus().get(0).getDishPrice());
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.get(0).getLocation());
        Log.d(Constants.TAG, " mArticleArrayList.size() " + mArticleArrayList.get(0).getAuthor().getName()
        );


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant_article_preview, parent, false);
        return new ArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ArticleItemViewHolder) {
            bindMainItem((ArticleItemViewHolder) holder, position);
        }

    }

    private void bindMainItem(ArticleItemViewHolder holder, int position) {

        holder.getAuthorName().setText(mArticleArrayList.get(position).getAuthor().getName());
        holder.getContent().setText(mArticleArrayList.get(position).getContent());

        Picasso.get()
                .load(mArticleArrayList.get(position).getPictures().get(0))
                .error(R.drawable.all_picture_placeholder)
                .transform(new CircleCornerForm(mContext))
                .resize(500, 300)
                .centerInside()
                .into(holder.getArticlePhoto());


    }

    @Override
    public int getItemCount() {
        return mArticleArrayList.size();
    }

    private class ArticleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mArticlePhoto;
        private TextView mAuthorName;
        private TextView mContent;

        public ArticleItemViewHolder(View view) {
            super(view);
            mArticlePhoto = view.findViewById(R.id.imageView_article_preview);
            mAuthorName = view.findViewById(R.id.textView_restaurant_preview_author_name);
            mContent = view.findViewById(R.id.textView_restaurant_preview_content);

            view.setOnClickListener(this);
        }

        public ImageView getArticlePhoto() {
            return mArticlePhoto;
        }

        public TextView getAuthorName() {
            return mAuthorName;
        }

        public TextView getContent() {
            return mContent;
        }

        @Override
        public void onClick(View view) {
            mPresenter.openPersonalArticle(mArticleArrayList.get(getAdapterPosition()));
        }
    }


}
