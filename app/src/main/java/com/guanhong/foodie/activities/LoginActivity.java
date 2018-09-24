package com.guanhong.foodie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.guanhong.foodie.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mNameEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }

    private void init() {
        setContentView(R.layout.activity_login);

        mNameEditText = findViewById(R.id.edittext_name);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mRegisterButton = findViewById(R.id.button_register);
        mLoginButton = findViewById(R.id.button_login);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_register) {

            if (mNameEditText == null || mPasswordEditText == null) {
                Toast.makeText(this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
            }

        } else if (view.getId() == R.id.button_login) {
            if (mNameEditText == null || mPasswordEditText == null) {
                Toast.makeText(this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isUserTokenExist() {
        return false;
    }
}
