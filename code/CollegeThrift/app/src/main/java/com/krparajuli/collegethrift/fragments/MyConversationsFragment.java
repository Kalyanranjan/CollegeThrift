package com.krparajuli.collegethrift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Conversation;
import com.krparajuli.collegethrift.viewholders.ConversationViewHolder;

public abstract class MyConversationsFragment extends Fragment {

    private static final String TAG = "MyConversationsFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseAuth mAuth;

    private FirebaseRecyclerAdapter<Conversation, ConversationViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public MyConversationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        View rootView = inflater.inflate(R.layout.fragment_my_messages, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.conversations_recycler_view);
        Log.d(TAG, "onCreateView: HELLO");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Setup LayoutManager
        mManager = new LinearLayoutManager(getActivity());
        mManager.setStackFromEnd(false);
        mRecycler.setLayoutManager(mManager);

        //Set up FirebaseRecyclerAdapter with the Query
        final Query conversationsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Conversation, ConversationViewHolder>(Conversation.class, R.layout.layout_conversation_item,
                ConversationViewHolder.class, conversationsQuery) {
            @Override
            protected void populateViewHolder(ConversationViewHolder viewHolder, final Conversation conversation, int position) {
                final DatabaseReference conversationRef = getRef(position);

                Log.d(TAG, "populateViewHolder: HELLO");
                viewHolder.bindToConversation(conversation);
            }
        };

        mRecycler.setAdapter(mAdapter);
    }

    public abstract Query getQuery(DatabaseReference databaseRef);
}
