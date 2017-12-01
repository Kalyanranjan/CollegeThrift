package com.krparajuli.collegethrift.Activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DatabaseReference;
import com.krparajuli.collegethrift.FBDatabase;
import com.krparajuli.collegethrift.Model.Listing;
import com.krparajuli.collegethrift.Model.ListingsAdapter;
import com.krparajuli.collegethrift.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewListingsActivityFragment extends Fragment {

    ArrayList<Listing> dataSet = new ArrayList<Listing>();
    GridView gridView;
    ListingsAdapter listingsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listingsFragment =  inflater.inflate(R.layout.fragment_view_listings, container, false);

        DatabaseReference listingsRef = FBDatabase.getListingsDbRef();


        gridView = (GridView) listingsFragment.findViewById(R.id.vl_grid);
        populateDataSet();
        listingsAdapter = new ListingsAdapter(dataSet, getActivity());

        gridView.setAdapter(listingsAdapter);
        return listingsFragment;
    }

    private void populateDataSet() {
        Listing lm = new Listing(
                "Iphone 6s",
                "Mint",
                true,
                true,
                false,
                120,
                "Asdasdasdasd"
        );
        dataSet.add(lm);


        Listing tm = new Listing(
                "sdasdas",
                "Second Listings",
                false,
                false,
                true,
                0,
                "asdasdasd");
        dataSet.add(tm);
    }

    public void getListings() {

    }
}
