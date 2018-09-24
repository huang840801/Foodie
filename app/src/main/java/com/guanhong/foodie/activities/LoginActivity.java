package com.guanhong.foodie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.guanhong.foodie.R;
import com.guanhong.foodie.util.Constants;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private Button mLoginButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        getFirebase();

        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }

    private void getFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(Constants.TAG, "登入:  " + user.getUid());
                    mUserId = user.getUid();

                    Intent intent = new Intent(LoginActivity.this, FoodieActivity.class);
                    startActivity(intent);
                    finish();

                } else {
//                    Toast.makeText(LoginActivity.this, "登入失敗!", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    private void init() {
        setContentView(R.layout.activity_login);

        mEmailEditText = findViewById(R.id.edittext_email);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mRegisterButton = findViewById(R.id.button_register);
        mLoginButton = findViewById(R.id.button_login);


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

        if (view.getId() == R.id.button_register) {
            Log.d(Constants.TAG, "  mNameEditText");
            if ("".equals(email) || "".equals(password)) {
                Toast.makeText(this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();

            } else {
                register(email, password);
            }


        } else if (view.getId() == R.id.button_login) {
            if ("".equals(email) || "".equals(password)) {
                Toast.makeText(this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "登入失敗!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }

    }

    private void register(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String message = task.isSuccessful() ? "註冊成功" : "註冊失敗";
                Log.d(Constants.TAG, "  message: "+ message);

                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, FoodieActivity.class);
                    startActivity(intent);
                    finish();
                }else if(password.length()<6){
                    Toast.makeText(LoginActivity.this, "密碼不能小於六碼!", Toast.LENGTH_SHORT).show();

                }else if(!email.contains("@")){
                    Toast.makeText(LoginActivity.this, "Email 格式錯誤!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(LoginActivity.this, "該用戶已存在!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
