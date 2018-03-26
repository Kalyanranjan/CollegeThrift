package com.krparajuli.collegethrift.fragments;

import com.krparajuli.collegethrift.fetchers.ESFetchMySold;
import com.krparajuli.collegethrift.models.ListingHitsObject;

import retrofit2.Call;

/**
 * Created by kal on 3/25/18.
 */

public class MyListingsSoldFragment extends ViewListingsFragment {

    public static final String TAG = "MyListingsSoldFrag";

    public MyListingsSoldFragment() { }

    protected Call<ListingHitsObject> getListingObjectsCall() {
        ESFetchMySold esFetchMySold = new ESFetchMySold(mElasticSearchPassword);
        Call<ListingHitsObject> call = esFetchMySold.getListingQueryCall();
        return call;
    }
}
