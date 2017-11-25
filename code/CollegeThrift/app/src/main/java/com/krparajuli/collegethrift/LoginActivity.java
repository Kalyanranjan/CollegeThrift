package com.krparajuli.collegethrift;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private String EMAIL_SUFFIX = "trincoll.edu";

    private EditText mLoginEmailAddress;
    private EditText mLoginPassword;
    private Button mLoginButton;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmailAddress = (EditText) findViewById(R.id.li_useremail);
        mLoginPassword = (EditText) findViewById(R.id.li_password);

        mSignupButton = (Button) findViewById(R.id.li_signup_button);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        mLoginButton = (Button) findViewById(R.id.li_login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserUsingCredentials();
            }
        });
    }

    private void loginUserUsingCredentials() {
        String userEmail = mLoginEmailAddress.getText().toString();
        String userPass = mLoginPassword.getText().toString();



    }

    public void hasCorrectEmailPrefix() {}

    public void hasCorrectEmailFormat() {}

    public void unregisteredUserWarningAndMessage() {

    }
}


//
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);
//
//    FloatingActionButton createListingsIcon = (FloatingActionButton) findViewById(R.id.fab);
//        createListingsIcon.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//        Intent createListingsIntent = new Intent(ViewListingsActivity.this, CreateListingsActivity.class);
//        startActivity(createListingsIntent);
//        }
//        });