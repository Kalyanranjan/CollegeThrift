package com.krparajuli.collegethrift;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kal on 11/22/17.
 */

public class FBDatabase {
    private static String LISTINGS_ROOT = "listings";
    private static String USERS_ROOT = "users";

    private static FirebaseDatabase mDbRef= null;
    private static DatabaseReference mListingsDbRef = null;
    private static DatabaseReference mUsersDbRef = null;

    private static String mDbError = "Database Error: ";
    private static String mDBSuccess = "Database Success: ";

    public static FirebaseDatabase getDb() {
        if (mDbRef == null)
            mDbRef = FirebaseDatabase.getInstance();
        if (mDbRef == null)
            dbConnErrorDisplay();

        return mDbRef;
    }

    private static DatabaseReference getReference(String ref) {
        FirebaseDatabase dbRef = getDb();
        if (dbRef == null)
            return null;
        if (mListingsDbRef == null) {
            mListingsDbRef = dbRef.getReference().child(ref);
        }
        return mListingsDbRef;
    }

    public static DatabaseReference getUsersDbRef() {
       return getReference(USERS_ROOT);
    }

    public static DatabaseReference getListingsDbRef() {
        return getReference(LISTINGS_ROOT);
    }

    public static void dbConnErrorDisplay() {
        Log.v(mDbError, "Could not connect to the remote database");
    }

}
