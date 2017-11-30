package com.krparajuli.collegethrift;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kal on 11/15/17.
 */

public class ListingsAdapter extends ArrayAdapter<Listing> implements View.OnClickListener {

    private ArrayList<Listing> dataSet;
    Context mContext;

    private int lastPosition = -1;

    private static class ViewHolder {
        ImageView listingThumbnail;
        TextView listingTitle;
    }

    public ListingsAdapter(ArrayList<Listing> data, Context context) {
        super(context, R.layout.listing_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Listing listing = (Listing) object;

        Log.v("Listing", "clicked");
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        Listing listing = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listing_item, parent, false);

            viewHolder.listingTitle = (TextView) convertView.findViewById(R.id.vlw_title);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.listingTitle.setText(listing.getTitle());
        return convertView;
    }
}
