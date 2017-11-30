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

    private String uid;
    private String title;
    private String description;
    private int type;
    private int price;

    public Listing() {}

    public Listing(String uid, String title, String desc, int type, int price) {
        this.uid = uid;
        this.title = title;
        this.description = desc;
        this.type = type;
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title", title);
        result.put("description", description);
        result.put("type", type);
        result.put("price", price);
        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
