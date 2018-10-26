package com.guanhong.foodie.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guanhong.foodie.R;
import com.guanhong.foodie.UserManager;
import com.guanhong.foodie.objects.User;
import com.guanhong.foodie.util.Constants;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private Button mLoginButton;
    private TextView mSignUpTextView;
    private TextView mLoginTextView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mName;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        loginWithFireBase();

        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mSignUpTextView.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);

    }

    private void loginWithFireBase() {

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(Constants.TAG, " LoginWithFirebase Id:  " + user.getUid());
                    Log.d(Constants.TAG, " LoginWithFirebase email:  " + user.getEmail());
                    Log.d(Constants.TAG, " LoginWithFirebase name:  " + mName);

                    //註冊新帳號
                    if (mName != null && !mName.equals("")) {


                        Log.d(Constants.TAG, " LoginWithFirebase : mName != null ");

                        Log.d(Constants.TAG, " LoginWithFirebase Id:  " + user.getUid());
                        Log.d(Constants.TAG, " LoginWithFirebase email:  " + user.getEmail());
                        Log.d(Constants.TAG, " LoginWithFirebase name:  " + mName);
                        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = userDatabase.getReference("user");

                        User user1 = new User();
                        user1.setName(mName);
                        user1.setEmail(user.getEmail());
                        user1.setId(user.getUid());
                        user1.setImage("");

                        UserManager userManager = UserManager.getInstance();

                        userManager.setUserData(user1);

                        myRef.child(user.getUid()).setValue(user1);
                    } else {
                        //用已有的帳號登入

                        Log.d(Constants.TAG, " LoginWithFirebase : mName == null ");

                        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = userDatabase.getReference("user");
                        Query query = myRef;
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (String.valueOf(snapshot.getKey()).equals(user.getUid())) {
                                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot);
                                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.getKey());
                                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.child("email").getValue());
                                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.child("id").getValue());
                                        Log.d(Constants.TAG, "FoodieActivityDataSnapshot : " + snapshot.child("image").getValue());
                                        Log.d(Constants.TAG, " FoodieActivityDataSnapshot : " + snapshot.child("name").getValue());

                                        User user = new User();
                                        user.setEmail((String) snapshot.child("email").getValue());
                                        user.setId((String) snapshot.child("id").getValue());
                                        user.setImage((String) snapshot.child("image").getValue());
                                        user.setName((String) snapshot.child("name").getValue());

                                        UserManager.getInstance().setUserData(user);

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    SharedPreferences userData = mContext.getSharedPreferences("userData", Context.MODE_PRIVATE);
                    userData.edit()
                            .putString("userId", user.getUid())
//                            .putString("userName", mName)
                            .commit();
                    Intent intent = new Intent(LoginActivity.this, FoodieActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };

    }


    private void init() {
        setContentView(R.layout.activity_login);

        mContext = this;

        mNameEditText = findViewById(R.id.edittext_name);
        mEmailEditText = findViewById(R.id.edittext_email);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mRegisterButton = findViewById(R.id.button_register);
        mLoginButton = findViewById(R.id.button_login);
        mSignUpTextView = findViewById(R.id.textView_sign_up);
        mLoginTextView = findViewById(R.id.textView_login);

        mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mNameEditText.setVisibility(View.GONE);
        mRegisterButton.setVisibility(View.GONE);
        mLoginTextView.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        Log.d(Constants.TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onClick(View view) {

        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();
        mName = mNameEditText.getText().toString();

        if (view.getId() == R.id.button_register) {

            if ("".equals(email) || "".equals(password) || "".equals(mName)) {
                Toast.makeText(this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();

            } else if (password.length() < 6) {

                Toast.makeText(LoginActivity.this, "密碼不能小於六碼!", Toast.LENGTH_SHORT).show();

            } else if (!email.contains("@")) {
                Toast.makeText(LoginActivity.this, "Email 格式錯誤!", Toast.LENGTH_SHORT).show();

            } else {

                register(email, password);
            }

        } else if (view.getId() == R.id.button_login) {
            if ("".equals(email) || "".equals(password)) {
                Toast.makeText(this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
            } else {
                login(email, password);
            }
        } else if (view.getId() == R.id.textView_sign_up) {
            mNameEditText.setVisibility(View.VISIBLE);
            mRegisterButton.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.GONE);
            mLoginTextView.setVisibility(View.VISIBLE);
            mSignUpTextView.setVisibility(View.GONE);

        } else if (view.getId() == R.id.textView_login) {
            mNameEditText.setVisibility(View.GONE);
            mRegisterButton.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginTextView.setVisibility(View.GONE);
            mSignUpTextView.setVisibility(View.VISIBLE);

        }

    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "登入失敗!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void register(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String message = task.isSuccessful() ? "註冊成功" : "該用戶已存在";
                Log.d(Constants.TAG, "  message: " + message);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()) {

//                    FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = userDatabase.getReference("user");
//              else {
//                    Toast.makeText(LoginActivity.this, "該用戶已存在!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
