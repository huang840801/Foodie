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
import com.guanhong.foodie.custom.Blur;
import com.guanhong.foodie.custom.CircleCornerForm;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.restaurant.RestaurantContract;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantArticlePreviewAdapter extends RecyclerView.Adapter {

    private RestaurantContract.Presenter mPresenter;

    private Context mContext;
    private ArrayList<Article> mArticleArrayList;

    public RestaurantArticlePreviewAdapter(ArrayList<Article> articleArrayList, RestaurantContract.Presenter presenter) {
        mArticleArrayList = articleArrayList;
        mPresenter = presenter;
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

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        long lcc = Long.valueOf(mArticleArrayList.get(position).getCreatedTime());
        String time = formatter.format(new Date(lcc));
        holder.getCreatedTime().setText(time);
        Picasso.get()
                .load(mArticleArrayList.get(position).getPictures().get(0))
                .fit()
                .placeholder(R.drawable.animated_rotate_drawable)
                .error(R.drawable.photo_error)
                .transform(new Blur(mContext))
//                .resize(500, 300)
//                .centerInside()
                .transform(new CircleCornerForm())
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
        private TextView mCreatedTime;

        public ArticleItemViewHolder(View view) {
            super(view);
            mArticlePhoto = view.findViewById(R.id.imageView_article_preview);
            mAuthorName = view.findViewById(R.id.textView_restaurant_preview_author_name);
            mContent = view.findViewById(R.id.textView_restaurant_preview_content);
            mCreatedTime = view.findViewById(R.id.textView_restaurant_preview_createdTime);

            view.setOnClickListener(this);
        }

        public TextView getCreatedTime() {
            return mCreatedTime;
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
