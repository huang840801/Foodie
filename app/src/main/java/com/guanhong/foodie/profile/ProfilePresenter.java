package com.guanhong.foodie.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

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
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.objects.Author;
import com.guanhong.foodie.objects.Menu;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;

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
//        getMyArticleData();
    }

    @Override
    public void getMyArticleData() {

        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        final String uid = userData.getString("userId", "");
//
//        Log.d(Constants.TAG, " ProfilePresenter uid = " + uid);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("article");
        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mArticleArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
                    if (snapshot.child("author").child("id").getValue().equals(uid)) {
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot);
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("author").child("id").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("author").child("image").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("author").child("name").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("content").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("location").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("restaurantName").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenterstarCount : " + snapshot.child("starCount").getValue());
                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("createdTime").getValue());
//                        Log.d(Constants.TAG, "ProfilePresenter: " + snapshot.child("pictures").getChildrenCount());

                        Article article = new Article();
                        Author author = new Author();
                        author.setId((String) snapshot.child("author").child("id").getValue());
                        author.setImage((String) snapshot.child("author").child("image").getValue());
                        author.setName((String) snapshot.child("author").child("name").getValue());
                        article.setAuthor(author);
                        article.setRestaurantName((String) snapshot.child("restaurantName").getValue());
                        article.setContent((String) snapshot.child("content").getValue());
                        article.setLocation((String) snapshot.child("location").getValue());
                        article.setCreatedTime(String.valueOf(snapshot.child("createdTime").getValue()));
                        article.setStarCount(Integer.parseInt("" + snapshot.child("starCount").getValue()));

                        ArrayList<String> pictures = new ArrayList<>();
                        for (int i = 0; i < snapshot.child("pictures").getChildrenCount(); i++) {
                            pictures.add((String) (snapshot.child("pictures").child(String.valueOf(i)).getValue()));
                        }
                        article.setPictures(pictures);

                        ArrayList<Menu> menuList = new ArrayList<>();
                        for (int i = 0; i < snapshot.child("menus").getChildrenCount(); i++) {
                            Menu menu = new Menu();
                            menu.setDishName((String) snapshot.child("menus").child(String.valueOf(i)).child("dishName").getValue());
                            menu.setDishPrice((String) snapshot.child("menus").child(String.valueOf(i)).child("dishPrice").getValue());
                            menuList.add(menu);
                        }
                        article.setMenus(menuList);

//                        Log.d(Constants.TAG, " ProfilePresenter  getAuthor getId = " + article.getAuthor().getId());
//                        Log.d(Constants.TAG, " ProfilePresenter  getAuthor getName = " + article.getAuthor().getName());
//                        Log.d(Constants.TAG, " ProfilePresenter  getRestaurantName = " + article.getRestaurantName());
//                        Log.d(Constants.TAG, " ProfilePresenter  getLocation = " + article.getLocation());
//                        Log.d(Constants.TAG, " ProfilePresenter  getMenus = " + article.getMenus().get(0).getDishName());
//                        Log.d(Constants.TAG, " ProfilePresenter  getMenus = " + article.getMenus().get(0).getDishPrice());
//                        Log.d(Constants.TAG, " ProfilePresenter getContent = " + article.getContent());
//                        Log.d(Constants.TAG, " ProfilePresenter getStarCount = " + article.getStarCount());
//                        Log.d(Constants.TAG, " ProfilePresenter getPictures = " + article.getPictures());
//                        Log.d(Constants.TAG, " ProfilePresenter latitude = " + article.getLatLng().latitude);
//                        Log.d(Constants.TAG, " ProfilePresenter longitude = " + article.getLatLng().longitude);
                        mArticleArrayList.add(article);
                    }

                }
                Log.d(Constants.TAG, " ProfilePresenter mArticleArrayList = " + mArticleArrayList.size());
                mProfileView.showMyArticles(mArticleArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void updateUserImageToFireBaseStorage(ArrayList<String> pictures) {
//        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
//        String uid = userData.getString("userUid", "");

//        Log.d(" updateUserImage  ", " ProfilePresenter  " + url);
        Log.d(" updateUserImage  ", " getUserId  " + UserManager.getInstance().getUserId());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("user");


        StorageReference mStorageReference;
        mStorageReference = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(pictures.get(0)));
        final StorageReference myRef = mStorageReference.child(UserManager.getInstance().getUserId() + file);

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
                    Log.d("updateUserImage ", " isSuccessful " + downloadUri);
                    Log.d("updateUserImage ", " getUserId " + UserManager.getInstance().getUserId());

                    databaseReference.child(UserManager.getInstance().getUserId()).child("image").setValue(downloadUri + "");
                    mProfileView.showUserNewPicture(downloadUri);
                }

            }
//                                        mPostView.showNewPictures(newPictures);

        });

    }

    @Override
    public void getUserData() {
        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        final String uid = userData.getString("userId", "");
        Log.d(Constants.TAG, " ProfilePresenteruid = " + uid);

//        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//
//        Query query = mDatabaseReference.child("user").orderByChild("id").equalTo(uid);
        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = userDatabase.getReference("user");
        Query query = myRef;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (String.valueOf(snapshot.getKey()).equals(uid)) {

                        Log.d(Constants.TAG, "ProfilePresenterDataSnapshot : " + snapshot.child("email").getValue());
                        Log.d(Constants.TAG, "ProfilePresenterDataSnapshot : " + snapshot.child("id").getValue());
                        Log.d(Constants.TAG, "ProfilePresenterDataSnapshot : " + snapshot.child("image").getValue());
                        Log.d(Constants.TAG, " ProfilePresenterDataSnapshot : " + snapshot.child("name").getValue());
                        User user = new User();

                        String name = snapshot.child("name").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String image = snapshot.child("image").getValue().toString();

                        user.setName(name);
                        user.setEmail(email);
                        user.setImage(image);

                        mProfileView.showUserData(user);

//                        Log.d(Constants.TAG, " name = " + name);
//                        Log.d(Constants.TAG, " email = " + email);
//                        Log.d(Constants.TAG, " image = " + image);

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

}
