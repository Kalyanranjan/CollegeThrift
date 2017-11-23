package com.krparajuli.collegethrift;

/**
 * Created by kal on 11/22/17.
 */

public class User {
    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private int mActivation;
    //private String timestamp

    public User() {}

    public User(String email, String password, String fname, String lname) {
        mEmail = email;
        mPassword = password;
        mFirstName = fname;
        mLastName = lname;
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

    public void activateUser() { setmActivation(0); }
}

