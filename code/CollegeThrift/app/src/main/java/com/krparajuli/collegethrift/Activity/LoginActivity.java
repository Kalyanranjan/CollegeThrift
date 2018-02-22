package com.krparajuli.collegethrift.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.krparajuli.collegethrift.Firebase.FBUserAuthentication;
import com.krparajuli.collegethrift.R;

public class LoginActivity extends AppCompatActivity {

    private String EMAIL_SUFFIX = "trincoll.edu";

    private EditText mLoginEmailAddress;
    private EditText mLoginPassword;
    private TextView mForgotPasswordLink;
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
                Log.v("Login", "Clicked Login");
                loginUserUsingCredentials();
            }
        });

        mForgotPasswordLink = (TextView) findViewById(R.id.li_forgotten_pass_text);
        mForgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPassIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(forgotPassIntent);
            }
        });


    }

    private void loginUserUsingCredentials() {
        String userEmail = mLoginEmailAddress.getText().toString().trim();
        String userPass = mLoginPassword.getText().toString().trim();

        boolean signedIn = FBUserAuthentication.signIn(userEmail, userPass, this);
        if (signedIn) {
//            Snackbar.make(this.findViewById(R.id.li_login_form), "Login Successful", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            Intent viewListingsIntent = new Intent(LoginActivity.this, ViewListingsActivity.class);
            startActivity(viewListingsIntent);
            finish();
        } else {
            Snackbar.make(this.findViewById(R.id.li_login_form), "Login Unsuccessful", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
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