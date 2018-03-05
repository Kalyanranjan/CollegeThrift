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
    private int price;
    private String thumbnailUrl;
    private String listerUid;
    private long dateListed;
    private int status; // 0 is active, 1 if dormant, 2 if sold, 3 if deleted

    public Listing() {}         // Default constructor required for calls to DataSnapshot.getValue(Post.class)

    public Listing(String title, String desc,
                   ListingType type, ListingCategory category,
                   int price, String thumbnailUrl,
                   String listerUid, long dateListed, int status) {
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.category = category;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.listerUid = listerUid;
        this.dateListed = dateListed;
        this.status = status;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> listing = new HashMap<>();
        listing.put("title", title);
        listing.put("desc", desc);
        listing.put("type", type);
        listing.put("category", category);
        listing.put("price", price);
        listing.put("thumbnailUrl", thumbnailUrl);
        listing.put("listerUid", listerUid);
        listing.put("dateListed", dateListed);
        listing.put("status", status);

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getListerUid() {
        return listerUid;
    }

    public void setListerUid(String listerUid) {
        this.listerUid = listerUid;
    }

    public long getDateListed() {
        return dateListed;
    }

    public void setDateListed(long dateListed) {
        this.dateListed = dateListed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
