package com.krparajuli.collegethrift.fragments;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.krparajuli.collegethrift.fetchers.ESFetch;
import com.krparajuli.collegethrift.fetchers.ESFetchMyCurrent;
import com.krparajuli.collegethrift.fetchers.ESFetchViewListings;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingHitsList;
import com.krparajuli.collegethrift.models.ListingHitsObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kal on 2/24/18.
 */

public class ViewListingsRecentFragment extends ViewListingsFragment{

    private static final String TAG = "ViewListingsRecentFrag";

    public ViewListingsRecentFragment() {}

    protected Call<ListingHitsObject> getListingObjectsCall() {
        ESFetchViewListings esFetchViewListings = new ESFetchViewListings(mElasticSearchPassword);
        Call<ListingHitsObject> call = esFetchViewListings.getListingQueryCall();
        return call;
    }
}
