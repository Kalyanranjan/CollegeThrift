package com.krparajuli.collegethrift.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.krparajuli.collegethrift.Firebase.FBUserAuthentication;
import com.krparajuli.collegethrift.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText mSignupUserFname;
    private EditText mSignupUserEmail;
    private EditText mSignupUserPassword;
    private Button mSignupUserSubmitButtom;

    private String userFname, userEmail, userPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignupUserFname = (EditText) findViewById(R.id.su_userfname);
        mSignupUserEmail = (EditText) findViewById(R.id.su_email);
        mSignupUserPassword = (EditText) findViewById(R.id.su_password);
        mSignupUserSubmitButtom = (Button) findViewById(R.id.su_sign_up_button);


        mSignupUserSubmitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFname = mSignupUserFname.getText().toString().trim();
                userEmail = mSignupUserEmail.getText().toString().trim();
                userPass = mSignupUserPassword.getText().toString().trim();

                FBUserAuthentication.signUp(userFname, userEmail, userPass, SignUpActivity.this);
            }
        });
    }


}
