package com.guanhong.foodie.post;

import static com.google.common.base.Preconditions.checkNotNull;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.Constants;

import java.io.File;
import java.util.ArrayList;


public class PostPresenter implements PostContract.Presenter {

    private PostContract.View mPostView;


    public PostPresenter(PostContract.View postView) {

        mPostView = checkNotNull(postView, "postView cannot be null");
        mPostView.setPresenter(this);

    }

    @Override
    public void start() {

    }


    @Override
    public void postArticle(final Article article) {

//        Log.d(Constants.TAG, " postArticle  getAuthor getId = " + article.getAuthor().getId());
//        Log.d(Constants.TAG, " postArticle  getAuthor getName = " + article.getAuthor().getName());
//        Log.d(Constants.TAG, " postArticle  getRestaurantName = " + article.getRestaurantName());
//        Log.d(Constants.TAG, " postArticle  getLocation = " + article.getLocation());
//        Log.d(Constants.TAG, " postArticle  getMenus = " + article.getMenus().get(0).getDishName());
//        Log.d(Constants.TAG, " postArticle  getMenus = " + article.getMenus().get(0).getDishPrice());
//        Log.d(Constants.TAG, " postArticle mStarCount = " + article.getContent());
//        Log.d(Constants.TAG, " postArticle getStarCount = " + article.getStarCount());
//        Log.d(Constants.TAG, " postArticle latitude = " + article.getLatLng().latitude);
//        Log.d(Constants.TAG, " postArticle longitude = " + article.getLatLng().longitude);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference restaurantDataBase = firebaseDatabase.getReference("restaurant");
        restaurantDataBase.child(article.getLat_lng()).push().setValue(article);

        DatabaseReference articleDataBase = firebaseDatabase.getReference("article");
        articleDataBase.push().setValue(article);

        mPostView.transToMap();
    }

    @Override
    public void uploadImage(ArrayList<String> pictureList) {
        final ArrayList<String> newPictures = new ArrayList<>();

        final int num = pictureList.size();
        for (int i = 0; i < pictureList.size(); i++) {


            StorageReference storageReference;
            storageReference = FirebaseStorage.getInstance().getReference();
//            Log.d("MULTIPLE_PICKER ", "" + article.getPictures().get(i));


            Uri file = Uri.fromFile(new File(pictureList.get(i)));
            final StorageReference myRef = storageReference.child(UserManager.getInstance().getUserId() + file);

            final int finalI = i;
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
                        Log.d("MULTIPLE_PICKER ", " isSuccessful " + downloadUri);
                        newPictures.add(String.valueOf(downloadUri));
                        if (newPictures.size() == num) {
                            mPostView.showNewPictures(newPictures);
                        }

                    }

                }
//                                        mPostView.showNewPictures(newPictures);

            });
//            if(newPictures.size() == finalI){
//                mPostView.showNewPictures(newPictures);
//            }
        }
//        uploadImage.showImageUriList(newPictures);

    }

    @Override
    public void addPictures() {
        mPostView.addPictures();
    }

    public void setAddress(String addressLine, LatLng latLng) {
        mPostView.showAddress(addressLine, latLng);
    }

    public void getPictures(ArrayList<String> stringArrayListExtra) {
        mPostView.showPictures(stringArrayListExtra);
    }
}
