package com.krparajuli.collegethrift.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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


    private File mProfileImage;
    private boolean mImageChanged;

    private TextView mFullNameView;
    private EditText mFullNameEdit;
    private ImageView mProfileImageView;
    private Button mNewImageButton;
    private Button mRemoveCurrentImageButton;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize all views (ImageView for User Profile Picture, EditText for User full name and TextView for User Email)
        mFullNameView = findViewById(R.id.pe_current_fullname_view);
        mFullNameEdit = findViewById(R.id.pe_fullname_edit);
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
                mFullNameView.setText(currentUser.getFullname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserFullName();
            }
        });

    }

    private void updateUserFullName() {
        String enteredFullname = mFullNameEdit.getText().toString();
        if (!TextUtils.isEmpty(enteredFullname)) {
            DatabaseReference userRef = mDatabase.getReference().child("users").child(mAuth.getCurrentUser().getUid().toString());
            userRef.child("fullname").setValue(enteredFullname);
        }
        finish();
    }
}
