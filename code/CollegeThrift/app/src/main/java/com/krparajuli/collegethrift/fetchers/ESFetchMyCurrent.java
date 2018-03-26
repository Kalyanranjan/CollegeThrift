package com.krparajuli.collegethrift.fetchers;

/**
 * Created by kal on 3/24/18.
 */

public class ESFetchMyCurrent extends ESFetch {

    public ESFetchMyCurrent(String elasticSearchPassword) {
        super(elasticSearchPassword);
    }

    @Override
    public String getQuery() {
        return "* status:0 listerUid:" + mUserId;
    }
}
