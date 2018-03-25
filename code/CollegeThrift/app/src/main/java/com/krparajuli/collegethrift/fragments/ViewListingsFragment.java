package com.krparajuli.collegethrift.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingHitsList;
import com.krparajuli.collegethrift.models.ListingHitsObject;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;
import com.krparajuli.collegethrift.utils.ListingListAdapter;

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
        mGridLayoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        executeQueryAndSetupListings(mDatabase);
    }

    protected void setupListingLists() {
        mListingAdapter = new ListingListAdapter(getActivity(), mListings);
        mRecyclerView.setAdapter(mListingAdapter);
    }

    public void executeQueryAndSetupListings(DatabaseReference databaseReference) {
        mListings = new ArrayList<Listing>();

        Call<ListingHitsObject> call = getListingObjectsCall();
        call.enqueue(new Callback<ListingHitsObject>() {
            @Override
            public void onResponse(Call<ListingHitsObject> call, Response<ListingHitsObject> response) {
                ListingHitsList listingHitsList = new ListingHitsList();
                String jsonResponse = "";

                try {
                    Log.d(TAG, "onResponse: server response: " + response.toString());
                    if (response.isSuccessful()) {
                        listingHitsList = response.body().getHits();
                    } else {
                        jsonResponse = response.errorBody().string();
                    }

                    Log.d(TAG, "onResponse: hits: " + listingHitsList);

                    for (int i = 0; i < listingHitsList.getListingIndex().size(); i++) {
                        mListings.add(listingHitsList.getListingIndex().get(i).getListing());
                    }

                    Log.d(TAG, "onResponse: size: " + mListings.size());
                    setupListingLists();

                } catch (NullPointerException e) {
                    Log.v(TAG, "onResponse: NullPointerException: " + e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    Log.v(TAG, "onResponse: IndexOutOfBoundsException:  " + e.getMessage());
                } catch (IOException e) {
                    Log.v(TAG, "onResponse: IOException:  " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListingHitsObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
//                Toast.makeText(getCallingActivity(), "search failed", Toast.LENGTH_SHORT).show(); //getActivity() gives error
            }
        });
    }

    protected abstract Call<ListingHitsObject>  getListingObjectsCall();
}
