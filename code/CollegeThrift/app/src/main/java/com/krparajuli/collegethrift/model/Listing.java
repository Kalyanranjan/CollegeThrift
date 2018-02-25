package com.krparajuli.collegethrift.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kal on 2/24/18.
 */

@IgnoreExtraProperties
public class Listing {

    private String title;
    private String desc;
    private ListingType type;
    private ListingCategory category;
    private String price;
    private String listerUid;
    private String dateListed;

    public Listing() {}         // Default constructor required for calls to DataSnapshot.getValue(Post.class)

    public Listing(String title, String desc, ListingType type, ListingCategory category, String price, String listerUid, String dateListed) {
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.category = category;
        this.price = price;
        this.listerUid = listerUid;
        this.dateListed = dateListed;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> listing = new HashMap<>();
        listing.put("title", title);
        listing.put("desc", desc);
        listing.put("type", type);
        listing.put("category", category);
        listing.put("price", price);
        listing.put("lister", listerUid);
        listing.put("date_listed", dateListed);

        return listing;
    }

    public String getTitle() {return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ListingType getType() {
        return type;
    }

    public void setType(ListingType type) {
        this.type = type;
    }

    public ListingCategory getCategory() {
        return category;
    }

    public void setCategory(ListingCategory category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getListerUid() {
        return listerUid;
    }

    public void setListerUid(String listerUid) {
        this.listerUid = listerUid;
    }

    public String getDateListed() {
        return dateListed;
    }

    public void setDateListed(String dateListed) {
        this.dateListed = dateListed;
    }
}
