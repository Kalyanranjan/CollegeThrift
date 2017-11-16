package com.krparajuli.collegethrift;

/**
 * Created by kal on 11/15/17.
 */

public class ListingsModel {

    private String mListingId;
    private String mListingTitle;
    private String mListingDesc;
    private boolean mForSale;
    private boolean mForTrade;
    private boolean mForGiveaway;
    private int mListingPrice = 0;

    public ListingsModel(String listingId, String title, String desc, boolean sale, boolean trade, boolean giveaway, int price) {
        this.mListingId = listingId;

        this.mListingTitle = title;
        this.mListingDesc = desc;
        this.mForSale = sale;
        this.mForTrade = trade;
        this.mForGiveaway = giveaway;
        this.mListingPrice = price;
    }
    public ListingsModel(String entry) {

    }


    public String getmListingId() {
        return mListingId;
    }

    public String getmListingTitle() {
        return mListingTitle;
    }

    public void setmListingTitle(String mListingTitle) {
        this.mListingTitle = mListingTitle;
    }

    public String getmListingDesc() {
        return mListingDesc;
    }

    public void setmListingDesc(String mListingDesc) {
        this.mListingDesc = mListingDesc;
    }

    public boolean ismForSale() {
        return mForSale;
    }

    public void setmForSale(boolean mForSale) {
        this.mForSale = mForSale;
    }

    public boolean ismForTrade() {
        return mForTrade;
    }

    public void setmForTrade(boolean mForTrade) {
        this.mForTrade = mForTrade;
    }

    public boolean ismForGiveaway() {
        return mForGiveaway;
    }

    public void setmForGiveaway(boolean mForGiveaway) {
        this.mForGiveaway = mForGiveaway;
    }

    public int getmListingPrice() {
        return mListingPrice;
    }

    public void setmListingPrice(int mListingPrice) {
        this.mListingPrice = mListingPrice;
    }


}
