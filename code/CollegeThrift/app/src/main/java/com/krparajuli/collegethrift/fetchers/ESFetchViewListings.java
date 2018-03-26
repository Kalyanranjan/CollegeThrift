package com.krparajuli.collegethrift.fetchers;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kal on 3/24/18.
 */

public class ESFetchViewListings extends ESFetch{

    public ESFetchViewListings(String elasticSearchPassword) {
        super(elasticSearchPassword);
    }

    @Override
    public String getQuery() {
        return "* status:0 !listerUid:" + mUserId;
    }
}
