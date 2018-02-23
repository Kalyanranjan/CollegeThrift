package com.krparajuli.collegethrift.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
import com.krparajuli.collegethrift.Firebase.FBDatabase;
import com.krparajuli.collegethrift.Firebase.FBStorage;
import com.krparajuli.collegethrift.Firebase.FBUserAuthentication;
import com.krparajuli.collegethrift.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class CreateListingActivity extends AppCompatActivity {
    // Layout Objects
    private EditText clTitle, clDesc, clPrice;
    private Spinner clType, clCategory;
    private Button clAddImage, clRemoveImage, clCancelListing,clCreateListing;

    // Camera Objects and Fields
    private ImageView clListingImage;

    private static final String PHOTO_KEY = "listing_image_photo";
    private String IMAGES_FOLDER_NAME = "CollegeThriftImages";
    private File returnedPhoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        clTitle = (EditText) findViewById(R.id.cl_edit_listing_title);
        clDesc = (EditText) findViewById(R.id.cl_edit_listing_desc);
        clPrice = (EditText) findViewById(R.id.cl_edit_price);
        clType = (Spinner) findViewById(R.id.cl_spinner_type);
        clCategory = (Spinner) findViewById(R.id.cl_spinner_category);
        clAddImage = (Button) findViewById(R.id.cl_button_add_image);
        clRemoveImage = (Button) findViewById(R.id.cl_button_remove_image);
        clCancelListing = (Button) findViewById(R.id.cl_button_cancel);
        clCreateListing = (Button) findViewById(R.id.cl_button_create);

        clListingImage = (ImageView) findViewById(R.id.cl_listing_thumb_image);

        EasyImage.configuration(this)
                .setImagesFolderName(IMAGES_FOLDER_NAME)
                .setCopyTakenPhotosToPublicGalleryAppFolder(false)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(false);

        // Add Image Button
        clAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(CreateListingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    EasyImage.openCamera(CreateListingActivity.this, 0);
                } else {
                    Nammu.askForPermission(CreateListingActivity.this, Manifest.permission.CAMERA, new PermissionCallback() {
                        @Override
                        public void permissionGranted() {
                            EasyImage.openCamera(CreateListingActivity.this, 0);
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

        clCancelListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.clearConfiguration(CreateListingActivity.this);
                finish();
            }
        });

        clCreateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DatabaseReference listingsRef = FBDatabase.getListingsDbRef();

                    HashMap<String, Object> inputObject = getCreateListingObject();
//                    Log.v(TAG, inputObject.toString());
                    listingsRef.push().setValue(inputObject);
                } catch (Exception e) {
                    Log.v("Error: ", "Database Connection");
                }
                finish();
            }
        });
    }

    private HashMap<String, Object> getCreateListingObject() {
        HashMap<String, Object> listing = new HashMap<>();
        listing.put("title", clTitle.getText().toString().trim());
        listing.put("desc", clDesc.getText().toString().trim());
        listing.put("category", clCategory.getSelectedItemId());
        listing.put("type", clType.getSelectedItemId());
        listing.put("price", clPrice.getText().toString().trim());
        listing.put("lister", FBUserAuthentication.getUser().getUid());

        return listing;
    }

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
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CreateListingActivity.this);
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
        Log.v("--------PHOTOS RETURNED", " size = "+returnedPhotosList.size());
        returnedPhoto = returnedPhotosList.get(0);
        Log.v("LULS", returnedPhotosList.toString());
//        if (returnedPhoto == null) {
            Log.v("Why", returnedPhoto.toString());
//        }
//          Picasso.with(this)
//                .load(returnedPhoto)
//                .fit()
//                .centerCrop()
//                .into(CreateListingActivity.clListingImage);

        clListingImage.setImageURI(Uri.fromFile(returnedPhoto));
    }

//    private Uri uploadListingThumbnailImageAndGetUrl() {
//        if (returnedPhoto == null)
//            return null;
//
//        Uri file = Uri.fromFile(returnedPhoto);
//        StorageReference thumbnailsRef = FBStorage.getListingThumbnailReference();
//        StorageReference imageRef = thumbnailsRef.child("ti1");
//
//        imageRef.putFile(file)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        FBStorage.setmLastTaskSnapshotUrl(downloadUrl);
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });
//        return FBStorage.readOncemLastSnapshotUrl();
//    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }
}
