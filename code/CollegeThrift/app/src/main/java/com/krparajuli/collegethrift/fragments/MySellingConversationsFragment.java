package com.krparajuli.collegethrift.fragments;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MySellingConversationsFragment extends MyConversationsFragment {
    public MySellingConversationsFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseRef) {
        return databaseRef.child("conversations")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("selling")
                .limitToFirst(100);
    }
}
