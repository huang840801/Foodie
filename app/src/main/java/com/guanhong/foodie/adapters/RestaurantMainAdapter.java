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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.UserManager;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Comment;
import com.guanhong.foodie.objects.Menu;
import com.guanhong.foodie.objects.Restaurant;
import com.guanhong.foodie.restaurant.RestaurantContract;
import com.guanhong.foodie.util.ArticlePreviewItemDecoration;
import com.guanhong.foodie.util.Constants;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.james.biuedittext.BiuEditText;

public class RestaurantMainAdapter extends RecyclerView.Adapter {

    private RestaurantContract.Presenter mPresenter;

    private Restaurant mRestaurant;
    private ArrayList<Comment> mComments = new ArrayList<>();

    private Context mContext;

    private boolean isLike = false;

    public RestaurantMainAdapter(RestaurantContract.Presenter presenter, Restaurant restaurant, ArrayList<Comment> comments) {

        Log.d("myCommentsBug ", "  RestaurantMainAdapter comments.size = " + comments.size());

        mPresenter = presenter;
        mRestaurant = restaurant;
        mComments = comments;

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
        return mComments.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? Constants.VIEWTYPE_RESTAURANT_MAIN : Constants.VIEWTYPE_RESTAURANT_COMMENT;
    }

    private void bindMainItem(final RestaurantMainItemViewHolder holder) {

        setPhotoRecyclerView(holder);
        getArticleFromFirebase(holder);

        holder.getRestaurantName().setText(mRestaurant.getRestaurantName());
        holder.getRestaurantPosition().setText(mRestaurant.getRestaurantLocation());

        if (mRestaurant.getStarCount() == 5) {
            holder.getStar1().setImageResource(R.drawable.new_star_selected);
            holder.getStar2().setImageResource(R.drawable.new_star_selected);
            holder.getStar3().setImageResource(R.drawable.new_star_selected);
            holder.getStar4().setImageResource(R.drawable.new_star_selected);
            holder.getStar5().setImageResource(R.drawable.new_star_selected);
        } else if (mRestaurant.getStarCount() == 4) {
            holder.getStar1().setImageResource(R.drawable.new_star_selected);
            holder.getStar2().setImageResource(R.drawable.new_star_selected);
            holder.getStar3().setImageResource(R.drawable.new_star_selected);
            holder.getStar4().setImageResource(R.drawable.new_star_selected);
            holder.getStar5().setImageResource(R.drawable.new_star_unselected);
        } else if (mRestaurant.getStarCount() == 3) {
            holder.getStar1().setImageResource(R.drawable.new_star_selected);
            holder.getStar2().setImageResource(R.drawable.new_star_selected);
            holder.getStar3().setImageResource(R.drawable.new_star_selected);
            holder.getStar4().setImageResource(R.drawable.new_star_unselected);
            holder.getStar5().setImageResource(R.drawable.new_star_unselected);
        } else if (mRestaurant.getStarCount() == 2) {
            holder.getStar1().setImageResource(R.drawable.new_star_selected);
            holder.getStar2().setImageResource(R.drawable.new_star_selected);
            holder.getStar3().setImageResource(R.drawable.new_star_unselected);
            holder.getStar4().setImageResource(R.drawable.new_star_unselected);
            holder.getStar5().setImageResource(R.drawable.new_star_unselected);
        } else if (mRestaurant.getStarCount() == 1) {
            holder.getStar1().setImageResource(R.drawable.new_star_selected);
            holder.getStar2().setImageResource(R.drawable.new_star_unselected);
            holder.getStar3().setImageResource(R.drawable.new_star_unselected);
            holder.getStar4().setImageResource(R.drawable.new_star_unselected);
            holder.getStar5().setImageResource(R.drawable.new_star_unselected);
        } else if (mRestaurant.getStarCount() == 0) {
            holder.getStar1().setImageResource(R.drawable.new_star_unselected);
            holder.getStar2().setImageResource(R.drawable.new_star_unselected);
            holder.getStar3().setImageResource(R.drawable.new_star_unselected);
            holder.getStar4().setImageResource(R.drawable.new_star_unselected);
            holder.getStar5().setImageResource(R.drawable.new_star_unselected);
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference likeDataBase = firebaseDatabase.getReference(Constants.LIKE).child(UserManager.getInstance().getUserId());
        Query query = likeDataBase;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.getKey().equals(mRestaurant.getLat_Lng())) {
                        Log.d("likeDataBase", " snapshot getKey = " + snapshot.getKey());
                        Log.d("likeDataBase", " Likeeeeee ");
                        holder.getBookmark().setImageResource(R.drawable.bookmark_selected);
                        isLike = true;

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.getTextViewPostArticle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.transToPost();

            }
        });

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.arrow);
        holder.mImageViewArrowRight.setAnimation(animation);

        holder.getBookmark().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLike) {
                    holder.getBookmark().setImageResource(R.drawable.bookmark_selected);
                    uploadMyLikedArticleToFirebase(mRestaurant.getLat_Lng(), mRestaurant);
                    isLike = true;

                    Toast.makeText(mContext, R.string.add_to_like, Toast.LENGTH_SHORT).show();
                } else {
                    holder.getBookmark().setImageResource(R.drawable.bookmark_unselected);
                    deleteMyLikedArticleToFirebase(mRestaurant.getLat_Lng());
                    isLike = false;

                    Toast.makeText(mContext, R.string.remove_from_like, Toast.LENGTH_SHORT).show();

                }
            }
        });

        holder.getButtonSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = holder.getEditTextComment().getText().toString();

                if (!"".equals(comment)) {

                    Log.d("comment", " getLat_Lng = " + mRestaurant.getLat_Lng());
                    Log.d("comment", " comment = " + comment);
                    Log.d("comment", " UserName = " + UserManager.getInstance().getUserName());
                    Log.d("comment", " UserId = " + UserManager.getInstance().getUserId());
                    Log.d("comment", " UserImage = " + UserManager.getInstance().getUserImage());
                    Log.d("comment", " currentTimeMillis = " + System.currentTimeMillis());
//                    Log.d("comment", " str = " + str);

                    final Comment comment1 = new Comment();

                    Author author = new Author();
                    author.setId(UserManager.getInstance().getUserId());
                    author.setImage(UserManager.getInstance().getUserImage());
                    author.setName(UserManager.getInstance().getUserName());

                    comment1.setAuthor(author);
                    comment1.setComment(comment);
                    comment1.setCreatedTime(System.currentTimeMillis() + "");

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                    DatabaseReference restaurantDataBase = firebaseDatabase.getReference(Constants.COMMENT);
                    restaurantDataBase.child(mRestaurant.getLat_Lng()).child(System.currentTimeMillis() + "").setValue(comment1);

                    holder.getEditTextComment().setText("");

                }

            }
        });

    }

    private void deleteMyLikedArticleToFirebase(String latLng) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference likeDataBase = firebaseDatabase.getReference(Constants.LIKE);
        likeDataBase.child(UserManager.getInstance().getUserId()).child(latLng).removeValue();
    }

    private void uploadMyLikedArticleToFirebase(String latLng, Restaurant restaurant) {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference likeDataBase = firebaseDatabase.getReference(Constants.LIKE);
        likeDataBase.child(UserManager.getInstance().getUserId()).child(latLng).setValue(restaurant);

    }

    private void setArticlePreviewRecyclerView(RestaurantMainItemViewHolder holder, ArrayList<Article> articleArrayList) {
        holder.getRecyclerViewArticlePreview().setLayoutManager(new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.getRecyclerViewArticlePreview().setHasFixedSize(true);
        holder.getRecyclerViewArticlePreview().addItemDecoration(new ArticlePreviewItemDecoration(10));
        holder.getRecyclerViewArticlePreview().setAdapter(new RestaurantArticlePreviewAdapter(articleArrayList, mPresenter));
    }

    private void getArticleFromFirebase(final RestaurantMainItemViewHolder holder) {
        Log.d(Constants.TAG, " RestaurantMainAdapter: " + mRestaurant.getLat_Lng());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.RESTAURANT).child(mRestaurant.getLat_Lng());

        Query query = databaseReference.orderByValue();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Article> articleArrayList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot);
                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot.child("author").child(Constants.ID).getValue());
                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot.child("author").child(Constants.IMAGE).getValue());
                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot.child("author").child(Constants.NAME).getValue());
                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot.child("starCount").getValue());
                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot.child("location").getValue());
                    Log.d(Constants.TAG, " RestaurantMainAdapter: " + snapshot.child("createdTime").getValue());

                    final Article article = new Article();

                    Author author = new Author();
                    author.setId((String) snapshot.child(Constants.AUTHOR).child(Constants.ID).getValue());
                    author.setImage((String) snapshot.child(Constants.AUTHOR).child(Constants.IMAGE).getValue());
                    author.setName((String) snapshot.child(Constants.AUTHOR).child(Constants.NAME).getValue());

                    article.setAuthor(author);
                    article.setRestaurantName((String) snapshot.child(Constants.RESTAURANT_NAME).getValue());
                    article.setContent((String) snapshot.child(Constants.CONTENT).getValue());
                    article.setLat_lng((String) snapshot.child(Constants.LAT_LNG).getValue());
                    article.setLocation((String) snapshot.child(Constants.LOCATION).getValue());
                    article.setStarCount(Integer.parseInt(String.valueOf(snapshot.child(Constants.STARCOUNT).getValue())));
                    article.setCreatedTime(String.valueOf(snapshot.child(Constants.CREATEDTIME).getValue()));

                    ArrayList<Menu> menus = new ArrayList<>();
                    for (int i = 0; i < snapshot.child(Constants.MENUS).getChildrenCount(); i++) {
                        Menu menu = new Menu();
                        menu.setDishName((String) snapshot.child(Constants.MENUS).child(String.valueOf(i)).child(Constants.DISHNAME).getValue());
                        menu.setDishPrice((String) snapshot.child(Constants.MENUS).child(String.valueOf(i)).child(Constants.DISHPRICE).getValue());
                        menus.add(menu);
                    }

                    article.setMenus(menus);

                    ArrayList<String> pictures = new ArrayList<>();
                    for (int i = 0; i < snapshot.child(Constants.PICTURES).getChildrenCount(); i++) {
                        pictures.add((String) snapshot.child(Constants.PICTURES).child(String.valueOf(i)).getValue());
                    }

                    article.setPictures(pictures);
                    articleArrayList.add(article);
                }
                setArticlePreviewRecyclerView(holder, articleArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setPhotoRecyclerView(final RestaurantMainItemViewHolder holder) {
        holder.getRecyclerViewPhotoGallery().setLayoutManager((new LinearLayoutManager(Foodie.getAppContext(), LinearLayoutManager.HORIZONTAL, false)));

        holder.getRecyclerViewPhotoGallery()
                .setAdapter(new RestaurantPhotoGalleryAdapter(mRestaurant.getRestaurantPictures()));
        new PagerSnapHelper().attachToRecyclerView(holder.getRecyclerViewPhotoGallery());
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
        //        private EditText mEditTextComment;
        private BiuEditText mBiuEditText;
        private ImageView mButtonSend;
        private PageIndicatorView mPageIndicatorView;
        private ImageView mBookmark;
        private TextView mTextViewArticleTitle;
        private TextView mTextViewPostArticle;
        private ImageView mImageViewArrowRight;

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
//            mEditTextComment = itemView.findViewById(R.id.editText_comments);
            mBiuEditText = itemView.findViewById(R.id.editText_comments);
            mButtonSend = itemView.findViewById(R.id.imageView_send);
            mPageIndicatorView = itemView.findViewById(R.id.indicator);
            mBookmark = itemView.findViewById(R.id.imageView_bookmark);
            mTextViewArticleTitle = itemView.findViewById(R.id.textView_restaurant_article_title);
            mTextViewPostArticle = itemView.findViewById(R.id.button_restaurant_post);
            mImageViewArrowRight = itemView.findViewById(R.id.imageView_arrow_right);
        }

        public ImageView getImageViewArrowRight() {
            return mImageViewArrowRight;
        }

        public TextView getTextViewPostArticle() {
            return mTextViewPostArticle;
        }

        public ImageView getBookmark() {
            return mBookmark;
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
            return mBiuEditText;
        }

        public ImageView getButtonSend() {
            return mButtonSend;
        }

        public PageIndicatorView getPageIndicatorView() {
            return mPageIndicatorView;
        }

        public TextView getTextViewArticleTitle() {
            return mTextViewArticleTitle;
        }
    }


    private void bindCommentItem(RestaurantCommentItemViewHolder holder, int i) {

        holder.getTextAuthorName().setText(mComments.get(i).getAuthor().getName());

        holder.getTextCommentContent().setText(mComments.get(i).getComment());
        if (!"".equals(mComments.get(i).getAuthor().getImage())) {
            Picasso.get()
                    .load(mComments.get(i).getAuthor().getImage())
                    .fit()
                    .into(holder.getImageAuthorImage());
        }

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        long lcc = Long.valueOf(mComments.get(i).getCreatedTime());
        String time = formatter.format(new Date(lcc));
        holder.getTextCreatedTime().setText(time);
    }

    private class RestaurantCommentItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageAuthorImage;
        private TextView mTextAuthorName;
        private TextView mTextCommentContent;
        private TextView mTextCreatedTime;

        public RestaurantCommentItemViewHolder(View itemView) {
            super(itemView);

            mImageAuthorImage = (ImageView) itemView.findViewById(R.id.imageView_authorImage);
            mTextAuthorName = (TextView) itemView.findViewById(R.id.textView_authorName);
            mTextCommentContent = (TextView) itemView.findViewById(R.id.textView_comment_content);
            mTextCreatedTime = (TextView) itemView.findViewById(R.id.textView_comment_createdTime);

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

        public TextView getTextCreatedTime() {
            return mTextCreatedTime;
        }
    }

}
