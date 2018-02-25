package com.krparajuli.collegethrift.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by kal on 2/24/18.
 */

public class ViewListingsRecentFragment extends ViewListingsFragment{

    public ViewListingsRecentFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_listings_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentListingsQuery = databaseReference.child("listings")
                .limitToFirst(100);
        // [END recent_listings_query]

        return recentListingsQuery;
    }
}
