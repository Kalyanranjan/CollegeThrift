package com.krparajuli.collegethrift.fetchers;

import com.krparajuli.collegethrift.models.Listing;

import java.util.ArrayList;

/**
 * Created by kal on 3/24/18.
 */

public abstract class ESFetch {


    public abstract ArrayList<Listing> prepareQuery();
}
