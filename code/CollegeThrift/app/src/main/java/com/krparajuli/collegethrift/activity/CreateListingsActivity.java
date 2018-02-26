package com.krparajuli.collegethrift.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.model.Listing;
import com.krparajuli.collegethrift.model.ListingCategory;
import com.krparajuli.collegethrift.model.ListingType;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class CreateListingsActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private static final String PHOTO_KEY = "listing_image_photo";
    private String IMAGES_FOLDER_NAME = "CollegeThriftImages";
    private File returnedPhoto = null;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    // Layout Objects
    private EditText clTitle, clDesc, clPrice;
    private Spinner clType, clCategory;
    private Button clAddImage, clRemoveImage;
    private ImageView clListingImage;

    private FloatingActionButton clSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listings);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]


        clTitle = (EditText) findViewById(R.id.cl_title_edit);
        clDesc = (EditText) findViewById(R.id.cl_desc_edit);
        clPrice = (EditText) findViewById(R.id.cl_edit_price);
        clType = (Spinner) findViewById(R.id.cl_spinner_type);
        clCategory = (Spinner) findViewById(R.id.cl_spinner_category);
        clAddImage = (Button) findViewById(R.id.cl_button_add_image);
        clRemoveImage = (Button) findViewById(R.id.cl_button_remove_image);
        clListingImage = (ImageView) findViewById(R.id.cl_listing_thumb_image);

        clSubmitButton = (FloatingActionButton) findViewById(R.id.cl_fab_submit);
        clSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        EasyImage.configuration(this)
                .setImagesFolderName(IMAGES_FOLDER_NAME)
                .setCopyTakenPhotosToPublicGalleryAppFolder(false)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(false);

        // Add Image Button
        clAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(CreateListingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    EasyImage.openCamera(CreateListingsActivity.this, 0);
                } else {
                    Nammu.askForPermission(CreateListingsActivity.this, Manifest.permission.CAMERA, new PermissionCallback() {
                        @Override
                        public void permissionGranted() {
                            EasyImage.openCamera(CreateListingsActivity.this, 0);
                        }

                        @Override
                        public void permissionRefused() {
                            // Send Snackbar regarding refused permission
                        }
                    });
                }
            }
        });

        clRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnedPhoto = null;
                clListingImage.setImageResource(R.drawable.ic_image);
            }
        });
    }

    private void submitPost() {
        final String title = clTitle.getText().toString();
        final String body = clDesc.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            clTitle.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            clDesc.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // final String userId = getUid();
        final String uid = "HASDSAD";


//        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
//
//                        // [START_EXCLUDE]
//                        if (user == null) {
//                            // User is null, error out
//                            Log.e(TAG, "User " + userId + " is unexpectedly null");
//                            Toast.makeText(NewPostActivity.this,
//                                    "Error: could not fetch user.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Write new post
//                            writeNewPost(userId, user.username, title, body);
//                        }
//
//                        // Finish this Activity, back to the stream
//                        setEditingEnabled(true);
//                        finish();
//                        // [END_EXCLUDE]
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//                        setEditingEnabled(true);
//                        // [END_EXCLUDE]
//                    }
//                });

        writeListingToDb(uid, "KALYAN", title, body);
        setEditingEnabled(true);
        finish();

        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        clTitle.setEnabled(enabled);
        clDesc.setEnabled(enabled);
        if (enabled) {
            clSubmitButton.setVisibility(View.VISIBLE);
        } else {
            clSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeListingToDb(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("listings").push().getKey();
        Listing listing = new Listing(title, body, ListingType.GIVEAWAY, ListingCategory.APPLIANCE, "120", userId, "asdasd");
        Map<String, Object> listingValues = listing.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, listingValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CreateListingsActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotosReturned(List<File> returnedPhotosList){
        if (returnedPhotosList.size() < 1) {
            Snackbar photoRetrieveError = Snackbar.make(findViewById(R.id.cl_button_add_image),
                    "Photo(s) Not Retrieved. Try Again",
                    Snackbar.LENGTH_SHORT);
            photoRetrieveError.setAction("Action", null).show();
            return;
        }
//        returnedPhoto = returnedPhotosList.get(0);
        clListingImage.setImageURI(Uri.fromFile(returnedPhotosList.get(0)));
        EasyImage.clearConfiguration(CreateListingsActivity.this);

    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }



    // Need to gather all entries (especially Spinner Enums) and username/id

}
