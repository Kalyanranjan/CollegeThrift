package com.krparajuli.collegethrift.Activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.krparajuli.collegethrift.ListingsAdapter;
import com.krparajuli.collegethrift.ListingsModel;
import com.krparajuli.collegethrift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewListingsActivityFragment extends Fragment {

    ArrayList<ListingsModel> dataSet = new ArrayList<ListingsModel>();
    GridView gridView;
    ListingsAdapter listingsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listingsFragment =  inflater.inflate(R.layout.fragment_view_listings, container, false);;

        Log.v("Here", "HEAR");
        gridView = (GridView) listingsFragment.findViewById(R.id.vl_grid);
        populateDataSet();
        listingsAdapter = new ListingsAdapter(dataSet, getActivity());

        gridView.setAdapter(listingsAdapter);
        return listingsFragment;
    }

    private void populateDataSet() {
        ListingsModel lm = new ListingsModel("hdasdjasd",
                "First Listing",
                "First Listing Desc",
                true,
                true,
                false,
                120);
        dataSet.add(lm);
        ListingsModel tm = new ListingsModel("sdasdas",
                "Second Listings",
                "Second Listing Desc",
                false,
                false,
                true,
                0);
        dataSet.add(tm);
    }
}
