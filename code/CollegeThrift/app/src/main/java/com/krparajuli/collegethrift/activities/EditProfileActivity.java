package com.krparajuli.collegethrift.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.User;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "EditProfileActivity";

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    private TextView mFullNameView;
    private EditText mFullNameEdit;
    private TextView mVenmoUsernameView;
    private EditText mVenmoUsernameEdit;

    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize all views (ImageView for User Profile Picture, EditText for User full name and TextView for User Email)
        mFullNameView = findViewById(R.id.pe_current_fullname_view);
        mFullNameEdit = findViewById(R.id.pe_fullname_edit);
        mVenmoUsernameView = findViewById(R.id.pe_venmo_username_view);
        mVenmoUsernameEdit = findViewById(R.id.pe_venmo_username_edit);
        mSubmitButton = findViewById(R.id.pe_fab_submit);

        // Intialize Database, Authentication, Storage and ImageLoader
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        DatabaseReference userRef = mDatabase.getReference().child("/users").child(mAuth.getCurrentUser().getUid().toString());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: USER GOTTN");
                User currentUser = dataSnapshot.getValue(User.class);
                mFullNameView.setText(currentUser.getFullname().trim());
                if ((currentUser.getVenmoUsername() != null) && !(currentUser.getVenmoUsername().trim().equals(""))) {
                    mVenmoUsernameView.setText(mVenmoUsernameView.getText().toString()
                                + "\n\n Your venmo username connected currently is: " + currentUser.getVenmoUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
                finish();
            }
        });

    }

    private void updateUserProfile() {
        String enteredFullname = mFullNameEdit.getText().toString().trim();
        String venmoUsername = mVenmoUsernameEdit.getText().toString().trim();
        DatabaseReference userRef = mDatabase.getReference().child("users").child(mAuth.getCurrentUser().getUid().toString());
        if (!TextUtils.isEmpty(enteredFullname)) {
           userRef.child("fullname").setValue(enteredFullname);
            UserProfileChangeRequest userProfileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(enteredFullname).build();
            mAuth.getCurrentUser().updateProfile(userProfileUpdate);
        }
        if (!TextUtils.isEmpty(venmoUsername)) {
            userRef.child("venmoUsername").setValue(venmoUsername);
        }
    }



}
