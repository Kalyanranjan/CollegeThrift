package com.krparajuli.collegethrift.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kal on 11/16/17.
 */
@IgnoreExtraProperties
public class Listing {

    // need to change all fields to mTitle.. and so on
    private String title;
    private String desc;
    private boolean sale;
    private boolean trade;
    private boolean giveaway;
    private int price;
    private String lister;

    public Listing() {}

    public Listing(String title, String desc,
                   boolean sale, boolean trade, boolean giveaway, int price,
                   String lister) {
        this.title = title;
        this.desc = desc;
        this.sale = sale;
        this.trade = trade;
        this.giveaway = giveaway;
        this.price = price;
        this.lister = lister;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> listing = new HashMap<>();
        listing.put("title", title);
        listing.put("desc", desc);
        listing.put("sale", sale);
        listing.put("trade", trade);
        listing.put("giveaway", giveaway);
        listing.put("price", price);
        listing.put("lister", lister);

        return listing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public boolean isTrade() {
        return trade;
    }

    public void setTrade(boolean trade) {
        this.trade = trade;
    }

    public boolean isGiveaway() {
        return giveaway;
    }

    public void setGiveaway(boolean giveaway) {
        this.giveaway = giveaway;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLister() {
        return lister;
    }

    public void setLister(String lister) {
        this.lister = lister;
    }
}
