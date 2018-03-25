package com.krparajuli.collegethrift.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by kal on 3/25/18.
 */

public class ESPasswordGetter {

    public static final String TAG = "ESPasswordGetter";

    public static String mElasticSearchPassword = "";

    public static void retrieveElasticSearchPaswordFromDb() {
        Log.d(TAG, "getElasticSearchPassword: retrieving elasticSearch password");

        Query query = FirebaseDatabase.getInstance().getReference()
                .child("elasticSearch")
                .orderByValue();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
                mElasticSearchPassword = singleSnapshot.getValue().toString();
                Log.d(TAG, "onDataChange: Retrieved password");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "DatabaseError: " + databaseError.getMessage());
            }
        });
    }

    public static String getmElasticSearchPassword() {
        return mElasticSearchPassword;
    }
}
