package com.krparajuli.collegethrift.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by kal on 3/22/18.
 */

public class ImageUploader {

    private static final String TAG = "ImageUploader";
    private static final String FIREBASE_LISTING_IMAGES_PATH = "listing-images/";
    private static final String IMAGE_UPLOAD_FAILURE_URL = "https://firebasestorage.googleapis.com/v0/b/collegethrift-base.appspot.com/o/listing-thumbnails%2Fti1?alt=media&token=c7c4ebc5-e017-47b2-98bf-3ec8c1c8992f";

    private File mImageFile;
    private String mListingId;
    private Context mContext;
    private String mImageDownloadUrl;
    private boolean mImageUploadSuccess = false;

    public ImageUploader(File mImageBytes, String mListingId, Context context) {
        this.mImageFile = mImageBytes;
        this.mListingId = mListingId;
        this.mContext = context;
    }
    public void setmImageFile(File mImageFile) {
        this.mImageFile = mImageFile;
    }

    public String getmListingId() {
        return mListingId;
    }

    public String getmImageDownloadUrl() {
        return mImageUploadSuccess ? mImageDownloadUrl : IMAGE_UPLOAD_FAILURE_URL;
    }

    private void uploadImage(String path) {
        Log.d(TAG, "uploadImage: Uploading Image");

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(path);

        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(mImageFile));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(mContext, "Upload Successful", Toast.LENGTH_SHORT).show();

                // insert the downloadUri into the firebase database
                mImageUploadSuccess = true;
                mImageDownloadUrl = taskSnapshot.getDownloadUrl().toString();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Upload Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void uploadImageThumbnail() {
        Log.d(TAG, "uploadImageThumbnail: ");
        uploadImage(FIREBASE_LISTING_IMAGES_PATH
                        + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/"
                        + mListingId + "/thumbnail/thumbnail-image");
    }
}
