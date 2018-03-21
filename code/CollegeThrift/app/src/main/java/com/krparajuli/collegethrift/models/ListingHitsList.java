package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kal on 3/13/18.
 */

@IgnoreExtraProperties
public class ListingHitsList {

    @SerializedName("hits")
    @Expose
    private List<ListingSource> listingIndex;

    public List<ListingSource> getListingIndex() {
        return listingIndex;
    }

    public void setListingIndex(List<ListingSource> listingIndex) {
        this.listingIndex = listingIndex;
    }
}
