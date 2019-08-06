package com.guanhong.foodie.post;

import static com.google.common.base.Preconditions.checkNotNull;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guanhong.foodie.mainActivity.FoodieContract;
import com.guanhong.foodie.util.UserManager;
import com.guanhong.foodie.objects.Article;
import com.guanhong.foodie.util.Constants;

import java.io.File;
import java.util.ArrayList;

public class PostPresenter implements PostContract.Presenter {

    private PostContract.View mPostView;
    private FoodieContract.Presenter mMainPresenter;

    public PostPresenter(PostContract.View postView) {

        mPostView = checkNotNull(postView, "postView cannot be null");
        mPostView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void postArticle(final Article article) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference restaurantDataBase = firebaseDatabase.getReference(Constants.RESTAURANT);
        restaurantDataBase.child(article.getLat_lng()).push().setValue(article);

        DatabaseReference articleDataBase = firebaseDatabase.getReference(Constants.ARTICLE);
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

            Uri file = Uri.fromFile(new File(pictureList.get(i)));
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
                        newPictures.add(String.valueOf(downloadUri));
                        if (newPictures.size() == num) {
                            mPostView.showNewPictures(newPictures);
                        }
                    }
                }
//                                        mPostView.showNewPictures(newPictures);

            });
        }
    }

    @Override
    public void addPictures() {
        mPostView.addPictures();
    }

    @Override
    public void pickMultiplePictures() {
        mMainPresenter.pickMultiplePictures();
    }

    @Override
    public void transToMap() {
        mMainPresenter.transToMap();
    }

    @Override
    public void transToPostChildMap() {
        mMainPresenter.transToPostChildMap();
    }

    @Override
    public void checkRestaurantExists() {
        mMainPresenter.checkRestaurantExists();
    }

    @Override
    public void showErrorToast() {
        mPostView.showErrorToast();
    }

    public void setAddress(String addressLine, LatLng latLng) {
        mPostView.showAddress(addressLine, latLng);
    }

    public void getPictures(ArrayList<String> stringArrayListExtra) {
        mPostView.showPictures(stringArrayListExtra);
    }

    public void setMainPresenter(FoodieContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
