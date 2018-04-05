package com.krparajuli.collegethrift.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingCategory;
import com.krparajuli.collegethrift.models.ListingType;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ListingDetailActivity extends AppCompatActivity {

    private static final String TAG = "ListingDetailActivity";

    public static final String EXTRA_LISTING_KEY = "listing_key";

    private ImageLoader mImageLoader;

    private DatabaseReference mListingReference;
    private ValueEventListener mListingListener;
    private String mListingKey;

    private Listing mListing;

    private ImageView mThumbnailView;
    private TextView mTitleView;
    private TextView mDescView;
    private TextView mTypeView;
    private TextView mPriceView;
    private TextView mCategoryView;
    private Button mContactLister;
    private Button mEditDeleteListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

        // Get ListingKey from Intent
        mListingKey = getIntent().getStringExtra(EXTRA_LISTING_KEY);
        if (mListingKey == null) {
            finish();
            Toast.makeText(this, "Could View the listing in detail due to an error", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("Must pass EXTRA_LISTING_KEY");
        }

        // Initialize Database
        mListingReference = FirebaseDatabase.getInstance().getReference()
                .child("listings").child(mListingKey);

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(this));

        //Initialize Views
        mTitleView = (TextView) findViewById(R.id.ld_title_view);
        mThumbnailView = (ImageView) findViewById(R.id.ld_thumb_image);
        mDescView = (TextView) findViewById(R.id.ld_desc_view);
        mTypeView = (TextView) findViewById(R.id.ld_type_view);
        mPriceView = (TextView) findViewById(R.id.ld_price_view);
        mCategoryView = (TextView) findViewById(R.id.ld_category_view);
        mContactLister = (Button) findViewById(R.id.ld_contact_button);
        mEditDeleteListing = (Button) findViewById(R.id.ld_edit_delete_button);

//        mContactLister.setEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the listing
        // [START listing_value_event_listener]
        ValueEventListener listingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Listing object and use the values to update the UI
                mListing = dataSnapshot.getValue(Listing.class);

                // [START_EXCLUDE]
                mTitleView.setText(mListing.getTitle());
                mDescView.setText(mListing.getDesc());
                mTypeView.setText("Type: " + getTypeText(mListing.getType()));
                mPriceView.setText("Price: $" + String.valueOf(mListing.getPrice()));
                mCategoryView.setText("Category: " + getCategoryText(mListing.getCategory()));
                mImageLoader.getInstance().displayImage(mListing.getThumbnailUrl(), mThumbnailView);

                boolean listedByCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().equals(mListing.getListerUid());
                if (listedByCurrentUser) {
                    mContactLister.setVisibility(View.GONE);
                    if (mListing.getStatus() == 0) {
                        mEditDeleteListing.setVisibility(View.VISIBLE);
                    } else {
                        mEditDeleteListing.setVisibility(View.GONE);
                    }
                } else {
                    mEditDeleteListing.setVisibility(View.GONE);
                    mContactLister.setVisibility(View.VISIBLE);
                }

                mContactLister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent messageListerIntent = new Intent(ListingDetailActivity.this, MessengerActivity.class);
                        messageListerIntent.putExtra(MessengerActivity.EXTRA_NEW_CONVERSATION_BOOLEAN_KEY, true);
                        messageListerIntent.putExtra(MessengerActivity.EXTRA_LISTING_UID_KEY, mListing.getUid());
                        messageListerIntent.putExtra(MessengerActivity.EXTRA_OTHER_USER_UID_KEY, mListing.getListerUid());
                        startActivity(messageListerIntent);
                    }
                });

                mEditDeleteListing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Launch EditListingActivity
                        Intent editListingIntent = new Intent(ListingDetailActivity.this, CreateListingsActivity.class);
                        editListingIntent.putExtra(CreateListingsActivity.EXTRA_EDIT_MODE_BOOLEAN_KEY, true);
                        editListingIntent.putExtra(CreateListingsActivity.EXTRA_EDIT_LISTINGS_KEY, mListing.getUid());
                        startActivity(editListingIntent);
                    }
                });
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

    private String getTypeText(ListingType type) {
        switch (type) {
            case SALE_ONLY:
                return "Sale Only";
            case SALE_TRADE:
                return "Sale or Trade";
            case TRADE_ONLY:
                return "Trade Only";
            default:
                return "Giveaway";
        }
    }

    private String getCategoryText(ListingCategory category) {
        switch (category) {
            case BOOK:
                return "Book";
            case GADGET:
                return "Gadget";
            case KITCHEN:
                return "Kitchen";
            case CLOTHING:
                return "Clothing";
            case APPLIANCE:
                return "Appliance";
            case STATIONERY:
                return "Stationery";
            case DECORATIVE:
                return "Decorative";
            default:
                return "Other";
        }
    }
}
