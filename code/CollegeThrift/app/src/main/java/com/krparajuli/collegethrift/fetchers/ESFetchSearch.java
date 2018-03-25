package com.krparajuli.collegethrift.fetchers;

import com.krparajuli.collegethrift.models.ListingCategory;
import com.krparajuli.collegethrift.models.ListingHitsObject;
import com.krparajuli.collegethrift.models.ListingType;

import retrofit2.Call;

/**
 * Created by kal on 3/24/18.
 */

public class ESFetchSearch extends ESFetch {

    private String mKeyword;
    private ListingCategory mCategory;
    private ListingType mType;
    private int mPriceFrom = 0;
    private int mPriceTo = 0;


    public ESFetchSearch(String elasticSearchPassword,
                         String mKeyword,
                         ListingCategory mCategory, ListingType mType,
                         int mPriceFrom, int mPriceTo) {
        super(elasticSearchPassword);
        this.mKeyword = mKeyword;
        this.mCategory = mCategory;
        this.mType = mType;
        this.mPriceFrom = mPriceFrom;
        this.mPriceTo = mPriceTo;
    }

    @Override
    public String getQuery() {
        String query = mKeyword.trim() + "*";

        if (mCategory != null) {
            query += "+category:" + mCategory.toString().trim();
        }

        if (mType != null) {
            query += "+type:" + mType.toString().trim();
        }

        query += " price:[" + String.valueOf(mPriceFrom).trim();
        query += " TO ";
        if (mPriceTo != 0)
            query += String.valueOf(mPriceTo).trim();
        else
            query += "*";
        query += "]";

        query += " status:0";

        return query;
    }
}
