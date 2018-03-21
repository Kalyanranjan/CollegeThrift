package com.krparajuli.collegethrift.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kal on 3/13/18.
 */

@IgnoreExtraProperties
public class ListingSource {

    @SerializedName("_source")
    @Expose
    private Listing listing;

    public ListingSource(Listing listing) {
        this.listing = listing;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
