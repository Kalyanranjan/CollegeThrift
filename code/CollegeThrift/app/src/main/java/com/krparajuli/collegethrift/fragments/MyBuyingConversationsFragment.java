package com.krparajuli.collegethrift.fragments;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyBuyingConversationsFragment extends MyConversationsFragment {

    public MyBuyingConversationsFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseRef) {
        return databaseRef.child("conversations")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("buying")
                .orderByChild("lastMessageTime");
    }
}
