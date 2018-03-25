package com.krparajuli.collegethrift.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.activities.CreateListingsActivity;
import com.krparajuli.collegethrift.activities.ListingDetailActivity;
import com.krparajuli.collegethrift.fetchers.ESFetch;
import com.krparajuli.collegethrift.fetchers.ESFetchSearch;
import com.krparajuli.collegethrift.fetchers.ESFetchViewListings;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.ListingCategory;
import com.krparajuli.collegethrift.models.ListingHitsList;
import com.krparajuli.collegethrift.models.ListingHitsObject;
import com.krparajuli.collegethrift.models.ListingType;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;
import com.krparajuli.collegethrift.utils.ListingListAdapter;
import com.krparajuli.collegethrift.viewholders.ListingViewHolder;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class ViewListingsFragment extends Fragment {

    private static final String TAG = "ViewListingsFragment";
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

    public ViewListingsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mElasticSearchPassword = ESPasswordGetter.getmElasticSearchPassword();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_view_listings, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listing_recycler_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up LayoutManager, reverse layout
        mGridLayoutManager = new GridLayoutManager(getActivity(), NUM_GRID_COLS);
        mGridLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        executeQueryAndSetupListings(mDatabase);
    }

    protected void setupListingLists() {
        mListingAdapter = new ListingListAdapter(getActivity(), mListings);
        mRecyclerView.setAdapter(mListingAdapter);
    }

    protected abstract void executeQueryAndSetupListings(DatabaseReference databaseReference);
}
