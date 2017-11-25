package com.krparajuli.collegethrift.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kal on 11/22/17.
 */

public class User {
    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private int mActivation;
    private int mTimestamp;

    public User() {}

    public User(String email, String password, String fname, String lname) {
        this.mEmail = email;
        this.mPassword = "pass";
        this.mFirstName = fname;
        this.mLastName = lname;
        this.mActivation = 292853;
        this.mTimestamp = 0;
    }

    public String getmEmail() { return mEmail; }
    private  void setmEmail(String mEmail) { this.mEmail = mEmail; }

    public String getmPassword() { return mPassword; }
    private void setmPassword(String mPassword) { this.mPassword = mPassword; }

    public String getmFirstName() { return mFirstName; }
    public void setmFirstName(String mFirstName) { this.mFirstName = mFirstName; }

    public String getmLastName() { return mLastName; }
    public void setmLastName(String mLastName) { this.mLastName = mLastName; }

    private int getmActivation() { return mActivation; }
    private void setmActivation(int mActivation) { this.mActivation = mActivation; }

    private int getmTimestamp() { return mTimestamp; }
    private void setmTimestamp(int timestamp) {this.mTimestamp = timestamp; }

    public void activateUser() { setmActivation(0); }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", mEmail);
        result.put("fname", mFirstName);
        result.put("lname", mLastName);
        result.put("password", mPassword);
        result.put("activation", mActivation);
        result.put("timestamp", mTimestamp);
        return result;
    }

}

