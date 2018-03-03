package com.krparajuli.collegethrift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;
import com.krparajuli.collegethrift.R;

public class VerificationResetActivity extends AppCompatActivity {

    private static final String TAG = "VerificationResetActivity";
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_reset);

        findViewById(R.id.vr_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference("/users");
            }
        });

        findViewById(R.id.vr_resend_verification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void resetPassword(String emailAddress) {

    }

    private void resendVerificationEmail(String emailAddress) {

    }
}
