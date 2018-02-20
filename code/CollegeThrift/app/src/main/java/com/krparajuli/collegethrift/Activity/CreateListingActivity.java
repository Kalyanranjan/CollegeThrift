package com.krparajuli.collegethrift.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.krparajuli.collegethrift.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class CreateListingActivity extends AppCompatActivity {
    // Layout Objects


    // Camera Objects and Fields
    public static ImageView clListingImage;

    private static final String PHOTO_KEY = "listing_image_photo";
    private String IMAGES_FOLDER_NAME = "CollegeThriftImages";
    private File returnedPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        clListingImage = (ImageView) findViewById(R.id.cl_listing_thumb_image);

        EasyImage.configuration(this)
                .setImagesFolderName(IMAGES_FOLDER_NAME)
                .setCopyTakenPhotosToPublicGalleryAppFolder(false)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(false);

        // Add Image Button
        findViewById(R.id.cl_button_add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(CreateListingActivity.this, 0);
            }
        });
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

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }
}
