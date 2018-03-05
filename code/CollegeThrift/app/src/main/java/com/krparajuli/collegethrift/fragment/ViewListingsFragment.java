package com.krparajuli.collegethrift.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.krparajuli.collegethrift.activity.ListingDetailActivity;
import com.krparajuli.collegethrift.model.Listing;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.viewholder.ListingViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class ViewListingsFragment extends Fragment {

    private static final String TAG = "ViewListingsFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseAuth mAuth;

    private FirebaseRecyclerAdapter<Listing, ListingViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public ViewListingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_view_listings, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.listing_recycler_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up LayoutManager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        //Set up FirebaseRecyclerAdapter with the Query
        final Query listingsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Listing, ListingViewHolder>(Listing.class, R.layout.listing_item,
                ListingViewHolder.class, listingsQuery) {

            @Override
            protected void populateViewHolder(ListingViewHolder viewHolder, Listing listing, int position) {
                final DatabaseReference listingRef = getRef(position);

                // Set click listener for the whole post view
                final String listingKey = listingRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch ListingDetailsActivity
                        Intent listingDetailIntent = new Intent(getActivity(), ListingDetailActivity.class);
                        listingDetailIntent.putExtra(ListingDetailActivity.EXTRA_LISTING_KEY, listingKey);
                        startActivity(listingDetailIntent);
                    }
                });
                viewHolder.bindToListing(listing, mAuth.getInstance().getCurrentUser().getUid().toString());
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

//    public String getUid() {
//        return FirebaseAuth.getInstance().getCurrentUser().getUid();
//    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
