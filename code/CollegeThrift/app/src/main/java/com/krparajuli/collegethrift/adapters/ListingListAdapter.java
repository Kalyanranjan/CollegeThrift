package com.krparajuli.collegethrift.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.activities.CreateListingsActivity;
import com.krparajuli.collegethrift.activities.ListingDetailActivity;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingType;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by kal on 3/21/18.
 */

public class ListingListAdapter extends RecyclerView.Adapter<ListingListAdapter.ListingViewHolder> {
    private static final String TAG = "ListingListAdapter";
    private static int NUM_GRID_COLS = 1;

    private Context mContext;
    private ArrayList<Listing> mListings;
    private ImageLoader mImageLoader;

    private ValueEventListener mSwitchFavoritesListener = null;
    private ValueEventListener mFetchFavoritesListener = null;
    private DatabaseReference mFetchFavoritesDatabaseReference;
    private DatabaseReference mSwitchFavoritesDatabaseReference;

    public class ListingViewHolder extends RecyclerView.ViewHolder {

        private TextView mListingTitle, mListingDesc, mListingSale, mListingTrade, mListingGiveaway, mListingPrice, mListingCategory;
        private ImageView mListingThumbnail, mListingFavoriteEdit;
        private LinearLayout mListingFavoriteEditLayout;

        private boolean mIsFavorite;

        public ListingViewHolder(Context context, View listingView) {
            super(listingView);

            mContext = context;

            mListingThumbnail = (ImageView) listingView.findViewById(R.id.vlh_listing_thumbnail);
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

            mIsFavorite = false;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_listing_item, parent, false);
        return new ListingViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(final ListingViewHolder holder, int position) {
        final Listing listing = mListings.get(position);

        holder.mListingTitle.setText(listing.getTitle());
        holder.mListingDesc.setText(listing.getDesc());
        holder.mListingPrice.setText("$" + String.valueOf(listing.getPrice()));
        holder.mListingCategory.setText(listing.getCategory().toString());

        if (listing.getType() == ListingType.SALE_ONLY) {
            holder.mListingSale.setVisibility(View.VISIBLE);
            holder.mListingPrice.setVisibility(View.VISIBLE);
            holder.mListingTrade.setVisibility(View.GONE);
            holder.mListingGiveaway.setVisibility(View.GONE);
        } else if (listing.getType() == ListingType.SALE_TRADE) {
            holder.mListingSale.setVisibility(View.VISIBLE);
            holder.mListingTrade.setVisibility(View.VISIBLE);
            holder.mListingPrice.setVisibility(View.VISIBLE);
            holder.mListingGiveaway.setVisibility(View.GONE);
        } else if (listing.getType() == ListingType.TRADE_ONLY) {
            holder.mListingPrice.setVisibility(View.GONE);
            holder.mListingSale.setVisibility(View.GONE);
            holder.mListingTrade.setVisibility(View.VISIBLE);
            holder.mListingGiveaway.setVisibility(View.GONE);
        } else if (listing.getType() == ListingType.GIVEAWAY) {
            holder.mListingPrice.setVisibility(View.GONE);
            holder.mListingSale.setVisibility(View.GONE);
            holder.mListingTrade.setVisibility(View.GONE);
            holder.mListingGiveaway.setVisibility(View.VISIBLE);
        }

        if (listing.getStatus() != 0) {
            holder.mListingFavoriteEditLayout.setVisibility(View.GONE);
        }

        final Boolean listedByCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(listing.getListerUid());

        mImageLoader.getInstance().displayImage(mListings.get(position).getThumbnailUrl(), holder.mListingThumbnail);
        holder.mListingThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Image Clicked");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch ListingDetailsActivity
                Intent listingDetailIntent = new Intent(mContext, ListingDetailActivity.class);
                listingDetailIntent.putExtra(ListingDetailActivity.EXTRA_LISTING_KEY, listing.getUid());
                mContext.startActivity(listingDetailIntent);
            }
        });

        if (listedByCurrentUser) {
            holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_image_edit);
        } else {
            if (!holder.mIsFavorite) {
                holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_outline_24);
            } else {
                holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_24);
            }

            final String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mFetchFavoritesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("favorites").child(userUid).child(listing.getUid());

            mFetchFavoritesListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                    if (dataSnapshot.getValue() != null) {
                        holder.mIsFavorite = true;
                        holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_24);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mFetchFavoritesDatabaseReference.addListenerForSingleValueEvent(mFetchFavoritesListener);
        }

        holder.mListingFavoriteEditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listedByCurrentUser) {
                    //Launch EditListing
                    Intent editListingIntent = new Intent(mContext, CreateListingsActivity.class);
                    editListingIntent.putExtra(CreateListingsActivity.EXTRA_EDIT_LISTINGS_KEY, listing.getUid());
                    editListingIntent.putExtra(CreateListingsActivity.EXTRA_EDIT_MODE_BOOLEAN_KEY, true);
                    mContext.startActivity(editListingIntent);
                } else {
                    // Favorite Listing
                    switchFavorites(holder, listing);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListings.size();
    }

    public void switchFavorites(final ListingViewHolder holder, final Listing listing) {
        final String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mSwitchFavoritesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("favorites").child(userUid).child(listing.getUid());

        mSwitchFavoritesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(mContext, "Listing Favorited", Toast.LENGTH_SHORT).show();
                    holder.mIsFavorite = true;
                    mSwitchFavoritesDatabaseReference.setValue(listing.toMap());
                    holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_24);

                } else {
                    Toast.makeText(mContext, "Listing Unfavorited", Toast.LENGTH_SHORT).show();
                    holder.mIsFavorite = false;
                    mSwitchFavoritesDatabaseReference.removeValue();
                    holder.mListingFavoriteEdit.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mSwitchFavoritesDatabaseReference.addListenerForSingleValueEvent(mSwitchFavoritesListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mSwitchFavoritesListener != null) {
            mSwitchFavoritesDatabaseReference.removeEventListener(mSwitchFavoritesListener);
        }
        if (mFetchFavoritesListener != null){
            mFetchFavoritesDatabaseReference.removeEventListener(mFetchFavoritesListener);
        }
    }
}
