package com.krparajuli.collegethrift.Firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kal on 11/22/17.
 */

public class FBDatabase {
    private static String LISTINGS_ROOT = "listings";
    private static String USERS_ROOT = "users";

    private static FirebaseDatabase mDbInstance = null;
    private static DatabaseReference mListingsDbRef = null;
    private static DatabaseReference mUsersDbRef = null;

    private static String mDbError = "Database Error: ";
    private static String mDBSuccess = "Database Success: ";

    public static FirebaseDatabase instantiateDb(boolean persistence) {
        if (mDbInstance == null)
            mDbInstance = FirebaseDatabase.getInstance();
        if (mDbInstance == null)
            dbConnErrorDisplay();
        if (persistence)
            mDbInstance.setPersistenceEnabled(true);
        return mDbInstance;
    }

    public static void removeDBPersistence() {
        if (mDbInstance != null)
            mDbInstance.setPersistenceEnabled(false);
    }

    private static DatabaseReference getReference(String ref, boolean dbPersistence) {
        instantiateDb(dbPersistence);
        if (mDbInstance == null)
            return null;
        if (mListingsDbRef == null) {
            mListingsDbRef = mDbInstance.getReference().child(ref);
        }
        return mListingsDbRef;
    }

    public static DatabaseReference getReference(String ref) {
        return getReference(ref, false);
    }

    public static DatabaseReference getPersistentReference(String ref) { return getReference(ref, true); }

    public static DatabaseReference getUsersDbRef() {
       return getReference(USERS_ROOT, true);
    }

    public static DatabaseReference getListingsDbRef() {
        return getReference(LISTINGS_ROOT, true);
    }

    public static void dbConnErrorDisplay() {
        Log.v(mDbError, "Could not connect to the remote database");
    }

}
