package com.krparajuli.collegethrift.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingCategory;
import com.krparajuli.collegethrift.models.ListingType;
import com.krparajuli.collegethrift.utils.ImageUploader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class CreateListingsActivity extends AppCompatActivity {

    private static final String TAG = "CreateListingsActivity";
    private static final String REQUIRED = "Required";

    private static final String PHOTO_KEY = "listing_image_photo";
    private String IMAGES_FOLDER_NAME = "CollegeThriftImages";
    private static final String FALLBACK_IMAGE_DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/collegethrift-base.appspot.com/o/listing-thumbnails%2Fpicture-frame-with-mountain-image_318-40293.jpg?alt=media&token=b51734b8-1361-4179-8eda-6e1811fcc052";


    private File returnedPhoto = null;

    public static final String EXTRA_EDIT_MODE_BOOLEAN_KEY = "key_to_check_edit_mode";
    public static final String EXTRA_EDIT_LISTINGS_KEY = "key_of_listing_to_id";
    private static String mListingKey;
    private static boolean mEditMode;

    private DatabaseReference mListingReference;
    private ValueEventListener mListingListener = null;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    // [START declare_authentication_instance]
    private FirebaseAuth mAuthentication;
    // [END declare_authentication_instance]

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

        // [START declare_authentication_instance]
        mAuthentication = FirebaseAuth.getInstance();
        // [END declare_authentication_instance]


        mEditMode = getIntent().getBooleanExtra(EXTRA_EDIT_MODE_BOOLEAN_KEY, false);
        if (mEditMode) {
            mListingKey = getIntent().getStringExtra(EXTRA_EDIT_LISTINGS_KEY);
            // Initialize Database
            mListingReference = FirebaseDatabase.getInstance().getReference()
                    .child("listings").child(mListingKey);
        }

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
                submitOrEditPost();
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

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the listing
        // [START listing_value_event_listener]
        ValueEventListener listingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Listing object and use the values to update the UI
                Listing listing = dataSnapshot.getValue(Listing.class);
                // [END_EXCLUDE]
                clTitle.setText(listing.getTitle());
                clDesc.setText(listing.getDesc());
                clType.setSelection(listing.getType().getValue());
                clCategory.setSelection(listing.getCategory().getValue());
                clPrice.setText(String.valueOf(listing.getPrice()));
                //Get Image here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(CreateListingsActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        if (mEditMode) {
            readyForEdit();
            // [BEGIN listing_value_event_listener]
            mListingReference.addValueEventListener(listingListener);
            // [END listing_value_event_listener]

            // Keep copy of listing listener so we can remove it when app stops
            mListingListener = listingListener;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mListingListener != null) {
            mListingReference.removeEventListener(mListingListener);
        }
    }

    private void readyForEdit() {
        // Change title
        setTitle("Edit Listing");
        // Place it
    }

    private void submitOrEditPost() {
        final String title = clTitle.getText().toString();
        final String desc = clDesc.getText().toString();
        final int category = clCategory.getSelectedItemPosition();
        final int type = clType.getSelectedItemPosition();
        int price = 0;
        final long dateTimeEpoch = System.currentTimeMillis();
        final String listerId = mAuthentication.getCurrentUser().getUid(); // Need Error Check
        final int status = 0;


        // Title is required
        if (TextUtils.isEmpty(title)) {
            clTitle.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(desc)) {
            clDesc.setError(REQUIRED);
            return;
        }

        // Price is required if "Sale"
        if ((type == ListingType.SALE_ONLY.getValue()) || (type == ListingType.SALE_TRADE.getValue())) {
            final String priceText = clPrice.getText().toString();
            if (TextUtils.isEmpty(priceText)) {
                clPrice.setError(REQUIRED);
                return;
            } else {
                price = Integer.valueOf(priceText);
            }
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

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

        writeListingToDb(title, desc, category, type, price, FALLBACK_IMAGE_DOWNLOAD_URL, listerId, dateTimeEpoch, status);
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
    private void writeListingToDb(String title, String desc,
                                  int category, int type,
                                  int price, String thumbnailUrl,
                                  String listerUid, long dateTimeEpoch, int status) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("listings").push().getKey();
        if (mEditMode) {
            key = mListingKey;
        }

        ImageUploader imageUploader;
        if (returnedPhoto != null) {
            imageUploader = new ImageUploader(returnedPhoto, key, this);
            imageUploader.uploadImageThumbnail();
//            if (imageUploader.getmUploadTask() != null) {
//                imageUploader.getmUploadTask().get();
//            }
//            thumbnailUrl = imageUploader.getmImageDownloadUrl();
        }

        Listing listing = new Listing(key, title, desc,
                ListingType.values()[type], ListingCategory.values()[category],
                price, thumbnailUrl,
                listerUid, dateTimeEpoch, status);
        Map<String, Object> listingValues = listing.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/listings/" + key, listingValues);
        childUpdates.put("/user-listings/" + listerUid + "/" + key, listingValues);

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

    private void onPhotosReturned(List<File> returnedPhotosList) {
        if (returnedPhotosList.size() < 1) {
            Snackbar photoRetrieveError = Snackbar.make(findViewById(R.id.cl_button_add_image),
                    "Photo(s) Not Retrieved. Try Again",
                    Snackbar.LENGTH_SHORT);
            photoRetrieveError.setAction("Action", null).show();
            return;
        }
        returnedPhoto = returnedPhotosList.get(0);
        clListingImage.setImageURI(Uri.fromFile(returnedPhoto));
        EasyImage.clearConfiguration(CreateListingsActivity.this);
    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }
}
