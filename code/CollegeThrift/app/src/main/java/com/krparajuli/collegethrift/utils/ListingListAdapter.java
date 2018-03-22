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

import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Listing;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by kal on 3/21/18.
 */

public class ListingListAdapter  extends RecyclerView.Adapter<ListingListAdapter.ListingViewHolder> {
    private static final String TAG = "ListingListAdapter";
    private static int NUM_GRID_COLS = 1;

    private Context mContext;
    private ArrayList<Listing> mListings;


    public class ListingViewHolder extends RecyclerView.ViewHolder {

        private ImageView mListingThumbnail;

        public ListingViewHolder(View itemView) {
            super(itemView);

            mListingThumbnail = (ImageView) itemView.findViewById(R.id.vlh_listing_thumbnail);
            int gridWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth/NUM_GRID_COLS;
//            mListingThumbnail.setMaxHeight(imageWidth);
//            mListingThumbnail.setMaxWidth(imageWidth);
        }


    }


    public ListingListAdapter(Context context, ArrayList<Listing> listings) {
        mContext = context;
        mListings = listings;
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.layout_listing_item, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        //ImageLoader.getInstance().displayImage(mListings.get(position).getThumbnailUrl(), holder.mListingThumbnail);
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
