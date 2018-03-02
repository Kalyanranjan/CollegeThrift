package com.krparajuli.collegethrift.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kal on 3/2/18.
 */

@IgnoreExtraProperties
public class User {
    private String uid;
    private String email;
    private String fullname = "User";

    public User() {}

    public User(String uid, String email, String fullname) {
        this.uid = uid;
        this.email = email;
        this.fullname = fullname;
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("uid", uid);
        userMap.put("email", email);
        userMap.put("fullname", fullname);
        return userMap;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
