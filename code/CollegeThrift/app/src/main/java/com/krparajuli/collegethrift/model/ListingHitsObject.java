package com.krparajuli.collegethrift.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kal on 3/13/18.
 */

@IgnoreExtraProperties
public class ListingHitsObject {

    @SerializedName("hits")
    @Expose
    private ListingHitsList hits;

    public ListingHitsList getHits() {
        return hits;
    }

    public void setHits(ListingHitsList hits) {
        this.hits = hits;
    }
}

