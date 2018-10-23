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
import com.guanhong.foodie.profile.ProfileContract;
import com.guanhong.foodie.util.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileArticleAdapter extends RecyclerView.Adapter {

    private ProfileContract.Presenter mPresenter;
    private ArrayList<Article> mArticleArrayList;
//    private ArrayList<Bitmap> mBitmapList;
    private Context mContext;
    private Typeface mTypeface;


    public ProfileArticleAdapter(ArrayList<Article> articleArrayList, ProfileContract.Presenter presenter) {
        mArticleArrayList = articleArrayList;
//        mBitmapList = new ArrayList<>();
        mPresenter = presenter;
        Log.d(Constants.TAG, " mArticleArrayList " + mArticleArrayList.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_profile_article, parent, false);
        return new ProfileArticleAdapter.ProfileArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProfileArticleHolder) {
            bindArticleItem((ProfileArticleHolder) holder, position);
        }
    }

    private void bindArticleItem(final ProfileArticleHolder holder, int position) {
        holder.getRestaurantName().setText(mArticleArrayList.get(position).getRestaurantName());
        holder.getContent().setText(mArticleArrayList.get(position).getContent());


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        long lcc = Long.valueOf(mArticleArrayList.get(position).getCreatedTime());
        String time = formatter.format(new Date(lcc));
        holder.getCreatedTime().setText(time);




        Picasso.get()
                .load(mArticleArrayList.get(position).getPictures().get(0))
                .placeholder(R.drawable.animated_rotate_drawable)
                .error(R.drawable.photo_error)
                .transform(new Blur(mContext))
                .fit()
//                .resize(500,300)
//                .centerInside()
                .transform(new CircleCornerForm())
                .into(holder.getImageView());

    }



    @Override
    public int getItemCount() {
        return mArticleArrayList.size();
    }

    private class ProfileArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mRestaurantName;
        private TextView mContent;
        private TextView mCreatedTime;


        public ProfileArticleHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.imageView_profile_article_photo);
            mRestaurantName = view.findViewById(R.id.textView_profile_article_restaurantName);
            mContent = view.findViewById(R.id.textView_profile_article_content);
            mCreatedTime = view.findViewById(R.id.textView_profile_article_createdTime);

            view.setOnClickListener(this);

            setTypeFace();

        }

        private void setTypeFace() {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/GenJyuuGothicX-Bold.ttf");

            mRestaurantName.setTypeface(mTypeface);
            mContent.setTypeface(mTypeface);
            mCreatedTime.setTypeface(mTypeface);

        }

        public TextView getCreatedTime() {
            return mCreatedTime;
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public TextView getRestaurantName() {
            return mRestaurantName;
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
