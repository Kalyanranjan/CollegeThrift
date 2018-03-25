package com.krparajuli.collegethrift.utils;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingType;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by kal on 3/21/18.
 */

public class ListingListAdapter  extends RecyclerView.Adapter<ListingListAdapter.ListingViewHolder> {
    private static final String TAG = "ListingListAdapter";
    private static int NUM_GRID_COLS = 1;

    private Context mContext;
    private ArrayList<Listing> mListings;
    private ImageLoader mImageLoader;


    public class ListingViewHolder extends RecyclerView.ViewHolder {

        private TextView mListingTitle, mListingDesc, mListingSale, mListingTrade, mListingGiveaway, mListingPrice, mListingCategory;
        private ImageView mListingThumbnail, mListingFavoriteEdit;
        private LinearLayout mListingFavoriteEditLayout;

        public ListingViewHolder(View listingView) {
            super(listingView);

            mListingThumbnail = (ImageView) listingView.findViewById(R.id.vlh_listing_thumbnail);
            int gridWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth/NUM_GRID_COLS;
            mListingTitle = (TextView) listingView.findViewById(R.id.vlh_listing_title);
            mListingDesc = (TextView) listingView.findViewById(R.id.vlh_listing_desc);
            mListingSale = (TextView) listingView.findViewById(R.id.vlh_listing_sale);
            mListingTrade = (TextView) listingView.findViewById(R.id.vlh_listing_trade);
            mListingGiveaway = (TextView) listingView.findViewById(R.id.vlh_listing_giveaway);
            mListingPrice = (TextView) listingView.findViewById(R.id.vlh_listing_price);
            mListingCategory = (TextView) listingView.findViewById(R.id.vlh_listing_category);
            mListingThumbnail = (ImageView) listingView.findViewById(R.id.vlh_listing_thumbnail);
            mListingFavoriteEdit = (ImageView) listingView.findViewById(R.id.vlh_edit_favorite_image);
            mListingFavoriteEditLayout = (LinearLayout) listingView.findViewById(R.id.vlh_edit_favorite);
        }
    }

    public ListingListAdapter(Context context, ArrayList<Listing> listings) {
        mContext = context;
        mListings = listings;
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.layout_listing_item, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        Listing listing = mListings.get(position);
        holder.mListingTitle.setText(listing.getTitle());
        holder.mListingDesc.setText(listing.getDesc());
        holder.mListingPrice.setText("$"+String.valueOf(listing.getPrice()));
        holder.mListingCategory.setText(listing.getCategory().toString());

        if (listing.getType() == ListingType.SALE_ONLY) {
            holder.mListingSale.setVisibility(View.VISIBLE);
            holder.mListingPrice.setVisibility(View.VISIBLE);
        } else if (listing.getType() == ListingType.SALE_TRADE) {
            holder.mListingPrice.setVisibility(View.VISIBLE);
            holder.mListingTrade.setVisibility(View.VISIBLE);
            holder.mListingPrice.setVisibility(View.VISIBLE);
        } else if (listing.getType() == ListingType.TRADE_ONLY) {
            holder.mListingTrade.setVisibility(View.VISIBLE);
        } else if (listing.getType() == ListingType.GIVEAWAY) {
            holder.mListingGiveaway.setVisibility(View.VISIBLE);
        }

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(listing.getListerUid())) {
            holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_image_edit);
        } else {
            holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_outline_24);
        }

        mImageLoader.getInstance().displayImage(mListings.get(position).getThumbnailUrl(), holder.mListingThumbnail);
        holder.mListingThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Image Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListings.size();
    }


}
