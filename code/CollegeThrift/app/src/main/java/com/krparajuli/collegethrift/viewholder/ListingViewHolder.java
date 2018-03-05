package com.krparajuli.collegethrift.viewholder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krparajuli.collegethrift.model.Listing;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.model.ListingType;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by kal on 2/24/18.
 */

public class ListingViewHolder extends RecyclerView.ViewHolder{

    private TextView listingTitle, listingDesc, listingSale, listingTrade, listingGiveaway, listingPrice, listingCategory;
    private ImageView listingThumbnail, listingFavoriteEdit;

    public ListingViewHolder(View listingView) {
        super(listingView);

        listingTitle = (TextView) listingView.findViewById(R.id.vlh_listing_title);
        listingDesc = (TextView) listingView.findViewById(R.id.vlh_listing_desc);
        listingSale = (TextView) listingView.findViewById(R.id.vlh_listing_sale);
        listingTrade = (TextView) listingView.findViewById(R.id.vlh_listing_trade);
        listingGiveaway = (TextView) listingView.findViewById(R.id.vlh_listing_giveaway);
        listingPrice = (TextView) listingView.findViewById(R.id.vlh_listing_price);
        listingCategory = (TextView) listingView.findViewById(R.id.vlh_listing_category);
        listingThumbnail = (ImageView) listingView.findViewById(R.id.vlh_listing_thumbnail);
        listingFavoriteEdit = (ImageView) listingView.findViewById(R.id.vlh_edit_favorite_image);
    }

    public void bindToListing(Listing listing, String signedInUserUid) {
        listingTitle.setText(listing.getTitle());
        listingDesc.setText(listing.getDesc());
        listingPrice.setText("$"+String.valueOf(listing.getPrice()));
        listingCategory.setText(listing.getCategory().toString());

        if (listing.getType() == ListingType.SALE_ONLY) {
            listingSale.setVisibility(View.VISIBLE);
            listingPrice.setVisibility(View.VISIBLE);
        } else if (listing.getType() == ListingType.SALE_TRADE) {
            listingPrice.setVisibility(View.VISIBLE);
            listingTrade.setVisibility(View.VISIBLE);
            listingPrice.setVisibility(View.VISIBLE);
        } else if (listing.getType() == ListingType.TRADE_ONLY) {
            listingTrade.setVisibility(View.VISIBLE);
        } else if (listing.getType() == ListingType.GIVEAWAY) {
            listingGiveaway.setVisibility(View.VISIBLE);
        }

        if (signedInUserUid.equals(listing.getListerUid())) {
            listingFavoriteEdit.setImageResource(R.drawable.ic_image_edit);
        } else {
            listingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_outline_24);
        }

    }
}
