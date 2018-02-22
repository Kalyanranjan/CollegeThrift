package com.krparajuli.collegethrift.Activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.FBDatabase;
import com.krparajuli.collegethrift.Model.ItemCategory;
import com.krparajuli.collegethrift.Model.Listing;
import com.krparajuli.collegethrift.Model.ListingsAdapter;
import com.krparajuli.collegethrift.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewListingsActivityFragment extends Fragment {

    ArrayList<Listing> dataSet = new ArrayList<Listing>();
    GridView gridView;
    ListingsAdapter listingsAdapter;

    AtomicInteger listingsCount = new AtomicInteger();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference listingsRef = FBDatabase.getListingsDbRef();
        listingsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.v("-----------", " "+prevChildKey);
                int newCount = listingsCount.incrementAndGet();
                dataSet.add(dataSnapshot.getValue(Listing.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKay) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long numChildren = dataSnapshot.getChildrenCount();
                //System.out.println(listingsCount.get() + " == " + numChildren);

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                   // Log.v("********************", iterator.next().toString());

                    Listing listing = iterator.next().getValue(Listing.class);
                    dataSet.add(listing);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listingsFragment =  inflater.inflate(R.layout.fragment_view_listings, container, false);
        gridView = (GridView) listingsFragment.findViewById(R.id.vl_grid);

        Log.v("ONCREATEVIEW", "----------------------------------------------------------");
//        populateDataSet();
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
                "120",
                "Asdasdasdasd",
                ItemCategory.GADGET
        );
        dataSet.add(lm);


        Listing tm = new Listing(
                "sdasdas",
                "Second Listings",
                false,
                false,
                true,
                "0",
                "asdasdasd",
                ItemCategory.OTHER);
        dataSet.add(tm);


        Listing sm = new Listing(
                "Monitor",
                "Third Listing",
                true,
                false,
                false,
                "40",
                "ME2",
                ItemCategory.APPLIANCE
        );
        dataSet.add(sm);
    }

}
