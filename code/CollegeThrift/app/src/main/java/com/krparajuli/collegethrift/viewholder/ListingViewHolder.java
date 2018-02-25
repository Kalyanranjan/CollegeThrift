package com.krparajuli.collegethrift.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.krparajuli.collegethrift.model.Listing;
import com.krparajuli.collegethrift.R;

/**
 * Created by kal on 2/24/18.
 */

public class ListingViewHolder extends RecyclerView.ViewHolder{

    public TextView listingTitle;

    public ListingViewHolder(View listingView) {
        super(listingView);

        listingTitle = (TextView) listingView.findViewById(R.id.vlh_listing_title);
    }

    public void bindToListing(Listing listing) {
        listingTitle.setText(listing.getTitle());
    }
}
