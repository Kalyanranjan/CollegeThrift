package com.krparajuli.collegethrift.fetchers;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.ListingHitsObject;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;
import com.krparajuli.collegethrift.utils.ElasticSearchAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by kal on 3/24/18.
 */

public abstract class ESFetch {

    private static final String BASE_URL = "http://35.225.53.194//elasticsearch/";
    private static final String ESPASSWORD = "nLcwSgtD7wwK";

    protected static String mUserId;

    public ESFetch(String elasticSearchPassword) {
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    protected HashMap<String, String> getHeaderMap() {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", Credentials.basic("user", ESPASSWORD));
        return headerMap;
    }

    public Call<ListingHitsObject> getListingQueryCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);

        return searchAPI.search(getHeaderMap(), "AND", getQuery());
    }

    public abstract String getQuery();
}
