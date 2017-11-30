package com.krparajuli.collegethrift.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.krparajuli.collegethrift.Firebase.FBUserAuthentication;
import com.krparajuli.collegethrift.R;

public class ResetPasswordActivity extends Activity {

    private EditText mForgotPasswordEmailAddress;
    private Button mForgotPasswordSubmitButton;

    private String mEmailAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mForgotPasswordEmailAddress = (EditText) findViewById(R.id.rp_useremail);

        mForgotPasswordSubmitButton = (Button) findViewById(R.id.rp_submit_button);

        // the following has to be moved to FBUserAuthentication

        mForgotPasswordSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FBUserAuthentication.getAuthInstance();
                 mEmailAddr = mForgotPasswordEmailAddress.getText().toString().trim();

                auth.sendPasswordResetEmail(mEmailAddr)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Snackbar.make(findViewById(R.id.rp_reset_form), "Email sent", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    finish();
                                } else {
                                    Snackbar.make(findViewById(R.id.rp_reset_form), "Email Not Sent. Try again.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
            }
        });

    }
}
