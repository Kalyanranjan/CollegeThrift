package com.krparajuli.collegethrift.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.krparajuli.collegethrift.R;

public class VerificationResetActivity extends AppCompatActivity {

    private static final String TAG = "VerificationResetActivity";
    private FirebaseDatabase mDatabase;
    private EditText mEmailField;
    private String mEmailAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_reset);

        mEmailField = (EditText) findViewById(R.id.vr_field_email);

        findViewById(R.id.vr_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getUserEmail()) {
                };
            }
        });

        findViewById(R.id.vr_resend_verification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getUserEmail()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mEmailAddress);
                    Toast.makeText(VerificationResetActivity.this, "Verification Email sent to "+mEmailAddress, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void resetPassword(String emailAddress) {

    }

    private void resendVerificationEmail(String emailAddress) {

    }

    private boolean getUserEmail() {
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
        } else if (!email.endsWith("@trincoll.edu")){
            mEmailField.setError("Not a valid trincoll.edu email");
        } else {
            mEmailField.setError(null);
            mEmailAddress = email;
            return true;
        }
        return false;
    }
}
