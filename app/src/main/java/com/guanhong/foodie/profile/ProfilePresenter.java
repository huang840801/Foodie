package com.guanhong.foodie.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.util.Constants;

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


    @Override
    public void updateUserImageToFireBase(String url) {
        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String uid = userData.getString("userUid", "");

        mDatabaseReference.child("user").child(uid).child("image").setValue(url);

    }

    @Override
    public void getUserImage(Context context) {
        SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
        final String uid = userData.getString("userUid", "");

        Query query = mDatabaseReference.child("user").orderByChild("id").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = new User();

                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();

                    user.setName(name);
                    user.setEmail(email);
                    user.setImage(image);

                    mProfileView.showUserData(user);

                    Log.d(Constants.TAG, " name = " + name);
                    Log.d(Constants.TAG, " email = " + email);
                    Log.d(Constants.TAG, " image = " + image);

                    ContentResolver cr = mContext.getContentResolver();
                    try {
                        //由抽象資料接口轉換圖檔路徑為Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(image)));
                        mProfileView.showUserPicture(bitmap);
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



    public void getPicture(Uri uri) {

        updateUserImageToFireBase(String.valueOf(uri));

        ContentResolver cr = mContext.getContentResolver();
        try {
            //由抽象資料接口轉換圖檔路徑為Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            mProfileView.showUserPicture(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(), e);
        }
    }
}
