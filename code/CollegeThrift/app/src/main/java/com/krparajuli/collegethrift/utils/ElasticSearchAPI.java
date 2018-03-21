package com.krparajuli.collegethrift.utils;

import com.krparajuli.collegethrift.models.ListingHitsObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

/**
 * Created by kal on 3/13/18.
 */

public interface ElasticSearchAPI {

    @GET("_search/")
    Call<ListingHitsObject> search(
        @HeaderMap Map<String, String> headers,
        @Query("default_operator") String operator, //1st Query (prepends '?')
        @Query("q") String query //Second Query (prepends '&'
    );
}
