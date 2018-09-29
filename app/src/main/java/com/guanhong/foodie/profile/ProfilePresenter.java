package com.guanhong.foodie.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guanhong.foodie.Foodie;
import com.guanhong.foodie.FoodiePresenter;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseReference;

    private Context mContext;

    public ProfilePresenter(ProfileContract.View profileView, Context context) {
        mProfileView = checkNotNull(profileView, "profileView cannot be null!");
        mContext = context;
        mProfileView.setPresenter(this);
    }

    @Override
    public void start() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

//    @Override
//    public void getNewPicture(Uri uri) {
//
//        Log.d("IMAGE!!!!!!   ", "getNewPicture: uri:  " + uri);
////        Log.d("PATH!!!!!!   ", "getNewPicture: path:  "+ path);
//
//
//        String path = getRealPathFromURI(Foodie.getAppContext(), uri);
//        if (path != null && !path.equals("")) {
//            mProfileView.showNewPicture(path);
//            uploadToFirebase(path);
//
//        }
//        Log.d("IMAGE!!!!!!   ", "getNewPicture: uri:  " + uri);
//        Log.d("PATH!!!!!!   ", "getNewPicture: path:  " + path);
//    }

    @Override
    public void uploadToFirebase(String path) {
        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String uid = userData.getString("userUid", "");


        Uri file = Uri.fromFile(new File(path));
        StorageReference pictureRef = mStorageRef.child(uid);

        UploadTask uploadTask = pictureRef.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                updateUserImageToFireBase(downloadUrl.toString());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }

    @Override
    public void updateUserImageToFireBase(String url) {
        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String uid = userData.getString("userUid", "");

        mDatabaseReference.child("user").child(uid).child("image").setValue(url);

    }

    @Override
    public void getUserImage(Context context) {
        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String uid = userData.getString("userUid", "");

        Query query = mDatabaseReference.child("user").orderByChild("id").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d(Constants.TAG, " snapshot = " + snapshot);
                    String image = snapshot.child("image").getValue().toString();
                    ContentResolver cr = mContext.getContentResolver();
                    try {
                        //由抽象資料接口轉換圖檔路徑為Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(image)));
                        mProfileView.showMypicture(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    @Override
//    public void getUserImage(Context context) {
//        SharedPreferences userData = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
//        String name = userData.getString("userName", "");
//        String email = userData.getString("userEmail", "");
//        String uid = userData.getString("userUid", "");
//
//        Log.d(Constants.TAG, " userName : " + name);
//        Log.d(Constants.TAG, " userEmail : " + email);
//        Log.d(Constants.TAG, " userUid : " + uid);
//
//
//        Query query = mDatabaseReference.child("user").orderByChild("id").equalTo(uid);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    Log.d(Constants.TAG, " userUid : " + snapshot);
//                    Log.d(Constants.TAG, " userUid : " + snapshot.child("image").getValue());
//
//                    String image = snapshot.child("image").getValue().toString();
//                    if (image != null) {
//                        mProfileView.showUserImage(image);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void getPicture(Uri uri) {

        updateUserImageToFireBase(String.valueOf(uri));

        ContentResolver cr = mContext.getContentResolver();
        try {
            //由抽象資料接口轉換圖檔路徑為Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            mProfileView.showMypicture(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(), e);
        }
    }
}
