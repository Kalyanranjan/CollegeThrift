package com.krparajuli.collegethrift.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.adapters.ListingListAdapter;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragment";
    private static int NUM_GRID_COLS = 1;

    // [START define_database_reference]
    protected DatabaseReference mDatabase;
    // [END define_database_reference]

    protected FirebaseAuth mAuth;

    protected String mElasticSearchPassword;

    protected ArrayList<Listing> mListings;

    protected RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ListingListAdapter mListingAdapter;

    private DatabaseReference mFavoritesReference;
    private ValueEventListener mFavoritesValueEventListener = null;

    public FavoritesFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mElasticSearchPassword = ESPasswordGetter.getmElasticSearchPassword();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favorites_recycler_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up LayoutManager, reverse layout
        mGridLayoutManager = new GridLayoutManager(getActivity(), NUM_GRID_COLS);
        mGridLayoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        executeQueryAndSetupListings(mDatabase);
    }

    protected void setupListingLists() {
        mListingAdapter = new ListingListAdapter(getActivity(), mListings);
        mRecyclerView.setAdapter(mListingAdapter);
    }

    public void executeQueryAndSetupListings(final DatabaseReference databaseReference) {
        mListings = new ArrayList<Listing>();

        mFavoritesReference = FirebaseDatabase.getInstance().getReference().child("favorites")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mFavoritesValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListings.clear();
                if (dataSnapshot.getValue() != null) {
                    Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                    while (iter.hasNext()) {
                        mListings.add(iter.next().getValue(Listing.class));
                    }
                }
                setupListingLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mFavoritesReference.addValueEventListener(mFavoritesValueEventListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mFavoritesValueEventListener != null) {
            mFavoritesReference.removeEventListener(mFavoritesValueEventListener);
        }
    }
}
