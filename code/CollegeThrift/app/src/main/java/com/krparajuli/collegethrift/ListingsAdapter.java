package com.krparajuli.collegethrift;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kal on 11/15/17.
 */

public class ListingsAdapter extends ArrayAdapter<ListingsModel> implements View.OnClickListener {

    private ArrayList<ListingsModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        ImageView listingThumbnail;
        TextView listingTitle;
    }

    public ListingsAdapter(ArrayList<ListingsModel> data, Context context) {
        super(context, R.layout.listing_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        ListingsModel listingsModel = (ListingsModel) object;

        Log.v("Listing", "clicked");
    }



}
