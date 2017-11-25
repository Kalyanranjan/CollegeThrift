package com.krparajuli.collegethrift.Firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

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


    public static boolean signIn(String email, String password, Activity activity) {
        instantiate();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
        return (mAuth.getCurrentUser() != null);
    };





}
