package com.krparajuli.collegethrift.fragments;

import com.krparajuli.collegethrift.fetchers.ESFetchMyCurrent;
import com.krparajuli.collegethrift.models.ListingHitsObject;

import retrofit2.Call;

/**
 * Created by kal on 3/25/18.
 */

public class MyListingsCurrentFragment extends ViewListingsFragment {

    public static final String TAG = "MyListingsCurrentFrag";

    public MyListingsCurrentFragment() { }

    @Override
    protected Call<ListingHitsObject> getListingObjectsCall() {
        ESFetchMyCurrent esFetchMyCurrent = new ESFetchMyCurrent(mElasticSearchPassword);
        Call<ListingHitsObject> call = esFetchMyCurrent.getListingQueryCall();
        return call;
    }
}
