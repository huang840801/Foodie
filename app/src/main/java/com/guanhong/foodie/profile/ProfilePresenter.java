package com.guanhong.foodie.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.util.UserManager;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Menu;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.util.Constants;

import java.io.File;
import java.util.ArrayList;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;
    private FoodieContract.Presenter mMainPresenter;

    private Context mContext;
    private ArrayList<Article> mArticleArrayList;

    public ProfilePresenter(ProfileContract.View profileView, Context context) {
        mProfileView = checkNotNull(profileView, "profileView cannot be null!");
        mContext = context;
        mArticleArrayList = new ArrayList<>();
        mProfileView.setPresenter(this);
    }

    @Override
    public void start() {
        getUserData();
    }

    @Override
    public void getMyArticleData() {

        SharedPreferences userData = mContext.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE);
        final String uid = userData.getString(Constants.USER_ID, "");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference(Constants.ARTICLE);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mArticleArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child(Constants.AUTHOR).child(Constants.ID).getValue().equals(uid)) {

                        final Article article = new Article();
                        final Author author = new Author();
                        author.setId((String) snapshot.child(Constants.AUTHOR).child(Constants.ID).getValue());
                        author.setImage((String) snapshot.child(Constants.AUTHOR).child(Constants.IMAGE).getValue());
                        author.setName((String) snapshot.child(Constants.AUTHOR).child(Constants.NAME).getValue());
                        article.setAuthor(author);
                        article.setRestaurantName((String) snapshot.child(Constants.RESTAURANT_NAME).getValue());
                        article.setContent((String) snapshot.child(Constants.CONTENT).getValue());
                        article.setLocation((String) snapshot.child(Constants.LOCATION).getValue());
                        article.setCreatedTime(String.valueOf(snapshot.child(Constants.CREATEDTIME).getValue()));
                        article.setStarCount(Integer.parseInt("" + snapshot.child(Constants.STARCOUNT).getValue()));

                        ArrayList<String> pictures = new ArrayList<>();
                        for (int i = 0; i < snapshot.child(Constants.PICTURES).getChildrenCount(); i++) {
                            pictures.add((String) (snapshot.child(Constants.PICTURES).child(String.valueOf(i)).getValue()));
                        }
                        article.setPictures(pictures);

                        ArrayList<Menu> menuList = new ArrayList<>();
                        for (int i = 0; i < snapshot.child(Constants.MENUS).getChildrenCount(); i++) {
                            Menu menu = new Menu();
                            menu.setDishName((String) snapshot.child(Constants.MENUS).child(String.valueOf(i)).child(Constants.DISHNAME).getValue());
                            menu.setDishPrice((String) snapshot.child(Constants.MENUS).child(String.valueOf(i)).child(Constants.DISHPRICE).getValue());
                            menuList.add(menu);
                        }
                        article.setMenus(menuList);
                        mArticleArrayList.add(article);
                    }

                }
                mProfileView.showMyArticles(mArticleArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void updateUserImageToFireBaseStorage(ArrayList<String> pictures) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER);

        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(pictures.get(0)));
        final StorageReference myRef = storageReference.child(UserManager.getInstance().getUserId() + file);

        myRef.putFile(file).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return myRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    databaseReference.child(UserManager.getInstance().getUserId()).child(Constants.IMAGE).setValue(downloadUri + "");
                    mProfileView.showUserNewPicture(downloadUri);
                }
            }

        });

    }

    @Override
    public void getUserData() {
        SharedPreferences userData = mContext.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE);
        final String uid = userData.getString(Constants.USER_ID, "");

        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        Query query = userDatabase.getReference(Constants.USER);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (String.valueOf(snapshot.getKey()).equals(uid)) {

                        User user = new User();

                        String name = snapshot.child(Constants.NAME).getValue().toString();
                        String email = snapshot.child(Constants.EMAIL).getValue().toString();
                        String image = snapshot.child(Constants.IMAGE).getValue().toString();

                        user.setName(name);
                        user.setEmail(email);
                        user.setImage(image);

                        mProfileView.showUserData(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void openPersonalArticle(Article article) {
        mProfileView.showPersonalArticleUi(article);
    }

    @Override
    public void pickSinglePicture() {
        mMainPresenter.pickSinglePicture();
    }

    @Override
    public void transToPersonalArticle(Article article) {
        mMainPresenter.transToPersonalArticle(article);
    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
