package com.krparajuli.collegethrift.fragments;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
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

    public void executeQueryAndSetupListings(DatabaseReference databaseReference){
        mListings = new ArrayList<Listing>();

        ESFetchViewListings esFetchViewListings = new ESFetchViewListings(mElasticSearchPassword);
        Call<ListingHitsObject> call = esFetchViewListings.getListingQueryCall();
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
}
