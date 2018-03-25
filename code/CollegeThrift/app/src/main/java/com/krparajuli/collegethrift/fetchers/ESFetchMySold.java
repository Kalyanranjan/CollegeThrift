package com.krparajuli.collegethrift.fetchers;

/**
 * Created by kal on 3/24/18.
 */

public class ESFetchMySold extends ESFetch {

    public ESFetchMySold(String elasticSearchPassword) {
        super(elasticSearchPassword);
    }

    @Override
    public String getQuery() {
        return "* status:1 listerUid:" + mUserId;
    }
}
