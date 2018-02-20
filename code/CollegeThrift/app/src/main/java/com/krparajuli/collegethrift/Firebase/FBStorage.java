package com.krparajuli.collegethrift.Firebase;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by kal on 2/20/18.
 */

public class FBStorage {
    private static String LISTING_THUMBNAILS_ROOT = "listing-thumbnails/";
    private static String LISTING_IMAGES_ROOT = "listing-images/";
    private static String USER_PROFILES_ROOT = "user-profiles/";

    private static StorageReference mStorageRef = null;
    private static StorageReference mListingThumbnailRef = null;
    private static StorageReference mListingImagesRef = null;
    private static StorageReference mUserProfileRef = null;

    private static String mStorageError = "Storage Error";
    private static String mStorageSuccess = "Storage Success";

    private static Uri mLastTaskSnapshotUrl = null;

    private static StorageReference instantiateStorage() {
        if (mStorageRef == null)
            mStorageRef = FirebaseStorage.getInstance().getReference();
        if (mStorageRef == null)
            storageErrorDisplay();

        return mStorageRef;
    }

    public static StorageReference getListingThumbnailReference() {
        instantiateStorage();
        if (mStorageRef == null)
                return null;
        if (mListingThumbnailRef == null)
            mListingThumbnailRef = mStorageRef.child(LISTING_THUMBNAILS_ROOT);
        if (mListingThumbnailRef == null)
            return null;

        return mListingThumbnailRef;
    }

    public static StorageReference getListingImagesReference() {
        instantiateStorage();
        if (mStorageRef == null)
            return null;
        if (mListingImagesRef == null)
            mListingImagesRef = mStorageRef.child(LISTING_THUMBNAILS_ROOT);
        if (mListingImagesRef == null)
            return null;

        return mListingImagesRef;
    }

    public static StorageReference getUserProfileReference() {
        instantiateStorage();
        if (mStorageRef == null)
            return null;
        if (mUserProfileRef == null)
            mUserProfileRef = mStorageRef.child(LISTING_THUMBNAILS_ROOT);
        if (mUserProfileRef == null)
            return null;

        return mUserProfileRef;
    }

    public static void setmLastTaskSnapshotUrl(Uri uri) {mLastTaskSnapshotUrl = uri; }

    public static Uri readOncemLastSnapshotUrl() {
        Uri temp = mLastTaskSnapshotUrl;
        mLastTaskSnapshotUrl = null;
        return temp;
    }

    private static void storageErrorDisplay() {
        Log.v("FBStorage", "Storage Connection Error");
    }
}
