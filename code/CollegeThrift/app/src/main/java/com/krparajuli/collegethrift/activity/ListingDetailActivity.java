package com.krparajuli.collegethrift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.model.Listing;

public class ListingDetailActivity extends AppCompatActivity {

    private static final String TAG = "ListingDetailActivity";

    public static final String EXTRA_LISTING_KEY = "listing_key";

    private DatabaseReference mListingReference;
    private ValueEventListener mListingListener;
    private String mListingKey;

    private ImageView mThumbnailView;
    private TextView mTitleView;
    private TextView mDescView;
    private TextView mTypeView;
    private TextView mPriceView;
    private TextView mCategoryView;
    private Button mContactLister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

        // Get ListingKey from Intent
        mListingKey = getIntent().getStringExtra(EXTRA_LISTING_KEY);
        if (mListingKey == null) {
            finish();
            Toast.makeText(this, "Could View the listing in detail due to an error", Toast.LENGTH_SHORT);
            throw new IllegalArgumentException("Must pass EXTRA_LISTING_KEY");
        }

        // Initialize Database
        mListingReference = FirebaseDatabase.getInstance().getReference()
                .child("listings").child(mListingKey);

        //Initialize Views
        mTitleView = (TextView) findViewById(R.id.ld_title_view);
        mThumbnailView = (ImageView) findViewById(R.id.ld_thumb_image);
        mDescView = (TextView) findViewById(R.id.ld_desc_view);
        mTypeView = (TextView) findViewById(R.id.ld_type_view);
        mPriceView = (TextView) findViewById(R.id.ld_price_view);
        mCategoryView = (TextView) findViewById(R.id.ld_category_view);
        mContactLister = (Button) findViewById(R.id.ld_contact_button);
        mContactLister.setEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener listingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Listing listing = dataSnapshot.getValue(Listing.class);
                // [START_EXCLUDE]
                mTitleView.setText(listing.getTitle());
                mDescView.setText(listing.getDesc());
                mTypeView.setText("Type: " + listing.getType().toString());
                mPriceView.setText("Price: $" + String.valueOf(listing.getPrice()));
                mCategoryView.setText("Category: " + listing.getCategory().toString());
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(ListingDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        // [BEGIN listing_value_event_listener]
        mListingReference.addValueEventListener(listingListener);
        // [END listing_value_event_listener]

        // Keep copy of listing listener so we can remove it when app stops
        mListingListener = listingListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mListingListener != null) {
            mListingReference.removeEventListener(mListingListener);
        }
    }


}
