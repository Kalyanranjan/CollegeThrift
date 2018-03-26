package com.krparajuli.collegethrift.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.fetchers.ESFetch;
import com.krparajuli.collegethrift.fetchers.ESFetchSearch;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingCategory;
import com.krparajuli.collegethrift.models.ListingHitsList;
import com.krparajuli.collegethrift.models.ListingHitsObject;
import com.krparajuli.collegethrift.models.ListingType;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;
import com.krparajuli.collegethrift.utils.ListingListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private static int NUM_GRID_COLS = 1;

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private ArrayList<Listing> mListings;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ListingListAdapter mListingAdapter;

    private EditText lsKeywordText, lsPriceFrom, lsPriceTo;
    private CheckBox lsTypeCheck, lsCategoryCheck, lsPriceCheck;
    private Spinner lsTypeSpinner, lsCategorySpinner;
    private Button lsSubmitButton;

    private boolean mCategorySearch = false, mTypeSearch = false, mPriceSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ESPasswordGetter.retrieveElasticSearchPaswordFromDb();
        setContentView(R.layout.activity_search);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecyclerView = (RecyclerView) findViewById(R.id.ls_recycler_view);
        mGridLayoutManager = new GridLayoutManager(this, NUM_GRID_COLS);
        mGridLayoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
                executeQueryAndSetupListings(mDatabase);
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
        if (mListingAdapter != null) {
            mListingAdapter = null;
        }
    }

    private void setupListingLists() {
        mListingAdapter = new ListingListAdapter(this, mListings);
        mRecyclerView.setAdapter(mListingAdapter);
    }

    private void executeQueryAndSetupListings(DatabaseReference databaseReference) {
        mListings = new ArrayList<Listing>();

        String searchKeywordString = lsKeywordText.getText().toString();
        ListingCategory searchCategory = null;
        ListingType searchType = null;
        int priceFrom = 0;
        int priceTo = 0;

        if (mCategorySearch) {
            searchCategory = ListingCategory.values()[lsCategorySpinner.getSelectedItemPosition()];
        }
        if (mTypeSearch) {
            searchType = ListingType.values()[lsTypeSpinner.getSelectedItemPosition()];
        }
        if (mPriceSearch) {
            if (!lsPriceFrom.getText().toString().equals("")) {
                priceFrom = Integer.valueOf(lsPriceFrom.getText().toString());
            }
            if (!lsPriceTo.getText().toString().equals("")) {
                priceTo = Integer.valueOf(lsPriceTo.getText().toString());
            }
        }

        ESFetch esFetchSearch = new ESFetchSearch(ESPasswordGetter.getmElasticSearchPassword(), searchKeywordString, searchCategory, searchType, priceFrom, priceTo);
        Call<ListingHitsObject> call = esFetchSearch.getListingQueryCall();
        call.enqueue(new Callback<ListingHitsObject>() {
            @Override
            public void onResponse(Call<ListingHitsObject> call, Response<ListingHitsObject> response) {
                ListingHitsList listingHitsList = new ListingHitsList();
                String jsonResponse = "";

                try {
                    Log.d(TAG, "onResponse: server response: " + response.toString());
                    if (response.isSuccessful()) {
                        listingHitsList = response.body().getHits();
                    } else {
                        jsonResponse = response.errorBody().string();
                    }

                    Log.d(TAG, "onResponse: hits: " + listingHitsList);

                    for (int i = 0; i < listingHitsList.getListingIndex().size(); i++) {
                        mListings.add(listingHitsList.getListingIndex().get(i).getListing());
                    }

                    Log.d(TAG, "onResponse: size: " + mListings.size());
                    setupListingLists();

                } catch (NullPointerException e) {
                    Log.v(TAG, "onResponse: NullPointerException: " + e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    Log.v(TAG, "onResponse: IndexOutOfBoundsException:  " + e.getMessage());
                } catch (IOException e) {
                    Log.v(TAG, "onResponse: IOException:  " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListingHitsObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
//                Toast.makeText(getCallingActivity(), "search failed", Toast.LENGTH_SHORT).show(); //getActivity() gives error
            }
        });
    }

    private void disableFilterOptions() {
        lsCategorySpinner.setEnabled(false);
        lsTypeSpinner.setEnabled(false);
        lsPriceFrom.setEnabled(false);
        lsPriceTo.setEnabled(false);
    }
}
