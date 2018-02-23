package com.krparajuli.collegethrift.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * NTS - Probably not required
 */

/**
 * Created by kal on 11/3/17.
 */

public class    ViewListingsAdapter extends BaseAdapter {

    private Context mContext;

    public ViewListingsAdapter(Context c) {
        mContext = c;
     }

    public int getCount() {
        return 2;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    //Create View for each listings item referenced by the adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView singleListingView;
        if (convertView == null) {
            singleListingView = new TextView(mContext);
            singleListingView.setText("HELLO");
            singleListingView.append("it's me");
        } else {
            singleListingView = (TextView) convertView;
        }
        return singleListingView;
    }

}
