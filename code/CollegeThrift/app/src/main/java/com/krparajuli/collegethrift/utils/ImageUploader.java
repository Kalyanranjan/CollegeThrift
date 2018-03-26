package com.krparajuli.collegethrift.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by kal on 3/22/18.
 */

public class ImageUploader {

    private static final String TAG = "ImageUploader";
    private static final String FIREBASE_LISTING_IMAGES_PATH = "listing-images/";
    private static final String IMAGE_UPLOAD_FAILURE_URL = "https://firebasestorage.googleapis.com/v0/b/collegethrift-base.appspot.com/o/listing-thumbnails%2Fpicture-frame-with-mountain-image_318-40293.jpg?alt=media&token=b51734b8-1361-4179-8eda-6e1811fcc052";
    private static final short LISTING_THUMBNAIL = 0;
    private static final short LISTING_IMAGE = 1;
    private static final short USER_PROFILE_IMAGE = 2;

    private File mImageFile;
    private String mListingId;
    private Context mContext;
    private String mImageDownloadUrl;
    private boolean mImageUploadSuccess = false;
    private UploadTask mUploadTask = null;

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

    public boolean ismImageUploadSuccess() {
        return mImageUploadSuccess;
    }

    public UploadTask getmUploadTask() {
        return mUploadTask;
    }

    private void uploadImage(String path, final short type) {
        Log.d(TAG, "uploadImage: Uploading Image");

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(path);

        mUploadTask = storageReference.putFile(Uri.fromFile(mImageFile));
        mUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(mContext, "Upload Successful", Toast.LENGTH_SHORT).show();

                // insert the downloadUri into the firebase database
                mImageUploadSuccess = true;
                mImageDownloadUrl = taskSnapshot.getDownloadUrl().toString();
                if (type == LISTING_THUMBNAIL) {
                    FirebaseDatabase.getInstance().getReference().child("listings").child(mListingId)
                            .child("thumbnailUrl").setValue(mImageDownloadUrl);
                }
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
                        + mListingId + "/thumbnail/thumbnail-image",
                LISTING_THUMBNAIL);
    }
}
