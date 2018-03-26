package com.krparajuli.collegethrift.fetchers;

/**
 * Created by kal on 3/24/18.
 */

public class ESFetchMyRemoved extends ESFetch {

    public ESFetchMyRemoved(String elasticSearchPassword) {
        super(elasticSearchPassword);
    }

    @Override
    public String getQuery() {
        return "* status:2 listerUid:" + mUserId;
    }
}
