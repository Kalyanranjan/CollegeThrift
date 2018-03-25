package com.krparajuli.collegethrift.fragments;

import com.krparajuli.collegethrift.fetchers.ESFetchMyRemoved;
import com.krparajuli.collegethrift.models.ListingHitsObject;

import retrofit2.Call;

/**
 * Created by kal on 3/25/18.
 */

public class MyListingsRemovedFragment extends ViewListingsFragment {

    public static final String TAG = "MyListingsRemovedFrag";

    public MyListingsRemovedFragment() { }

    protected Call<ListingHitsObject> getListingObjectsCall() {
        ESFetchMyRemoved esFetchMyRemoved = new ESFetchMyRemoved(mElasticSearchPassword);
        Call<ListingHitsObject> call = esFetchMyRemoved.getListingQueryCall();
        return call;
    }
}
