package com.krparajuli.collegethrift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.model.Listing;
import com.krparajuli.collegethrift.viewholder.ListingViewHolder;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private Query mQuery = null;

    private FirebaseRecyclerAdapter<Listing, ListingViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private EditText lsKeywordText, lsPriceFrom, lsPriceTo;
    private CheckBox lsTypeCheck, lsCategoryCheck, lsPriceCheck;
    private Spinner lsTypeSpinner, lsCategorySpinner;
    private Button lsSubmitButton;

    private boolean mCategorySearch = false, mTypeSearch = false, mPriceSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) findViewById(R.id.ls_recycler_view);
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        lsKeywordText = (EditText) findViewById(R.id.ls_keyword_edit);
        lsTypeSpinner = (Spinner) findViewById(R.id.ls_spinner_type);
        lsCategorySpinner = (Spinner) findViewById(R.id.ls_spinner_category);
        lsTypeCheck = (CheckBox) findViewById(R.id.ls_type_check);
        lsCategoryCheck = (CheckBox) findViewById(R.id.ls_category_check);
        lsPriceCheck = (CheckBox) findViewById(R.id.ls_price_check);
        lsPriceFrom = (EditText) findViewById(R.id.ls_price_from_edit);
        lsPriceTo = (EditText) findViewById(R.id.ls_price_to_edit);
        lsSubmitButton = (Button) findViewById(R.id.ls_search_button);

        disableFilterOptions();

        lsSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareQuery(mDatabase);
                mAdapter = new FirebaseRecyclerAdapter<Listing, ListingViewHolder>(Listing.class, R.layout.listing_item,
                        ListingViewHolder.class, mQuery) {

                    @Override
                    protected void populateViewHolder(ListingViewHolder viewHolder, Listing listing, int position) {
                        final DatabaseReference listingsRef = getRef(position);

                        // Set click listener for the whole post view
                        final String listingKey = listingsRef.getKey();
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Launch ListingDetailActivity here
                            }
                        });
                        viewHolder.bindToListing(listing);
                    }
                };
                mRecycler.setAdapter(mAdapter);
            }
        });

        lsTypeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeSearch = !mTypeSearch;
                lsTypeSpinner.setEnabled(mTypeSearch);
            }
        });


        lsCategoryCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategorySearch = !mCategorySearch;
                lsCategorySpinner.setEnabled(mCategorySearch);
            }
        });

        lsPriceCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPriceSearch = !mPriceSearch;
                lsPriceFrom.setEnabled(mPriceSearch);
                lsPriceTo.setEnabled(mPriceSearch);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public void prepareQuery(DatabaseReference databaseReference) {
        mQuery = databaseReference.child("listings")
                .limitToFirst(100);
    }

    public void disableFilterOptions() {
        lsCategorySpinner.setEnabled(false);
        lsTypeSpinner.setEnabled(false);
        lsPriceFrom.setEnabled(false);
        lsPriceTo.setEnabled(false);
        lsSubmitButton.setEnabled(false);
    }

    public void validateSubmitSearch() {

    }
}
