package com.krparajuli.collegethrift;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kal on 11/25/17.
 */

public class FBUserAuthentication {

    public static FirebaseAuth mAuth = null;

    public static void instantiate() {
        if (mAuth == null)
        mAuth = FirebaseAuth.getInstance();
        if (mAuth == null) {
            //Display error message
        }
    }

    public static FirebaseAuth getAuthInstance() {
        instantiate();
        return mAuth;
    }

    public static boolean userSignedIn() {
        instantiate();
        return (mAuth.getCurrentUser() != null);
    }

    public static FirebaseUser getUser() {
        instantiate();
        return mAuth.getCurrentUser();
    }





}
