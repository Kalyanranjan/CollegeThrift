package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by kal on 3/26/18.
 */

@IgnoreExtraProperties
public class Conversation {
    private String convUid;
    private String listingUid;
    private String listingTitle;
    private int listingPrice;
    private String otherUserUid;
    private String otherUserName;
    private String otherUserEmail;
    private String lastMessage;
    private long lastMessageTime;

    public Conversation() {
    }

    public Conversation(String convUid, String listingUid, String listingTitle, int listingPrice, String otherUserUid, String otherUserName, String otherUserEmail, String lastMessage, long lastMessageTime) {
        this.convUid = convUid;
        this.listingUid = listingUid;
        this.listingTitle = listingTitle;
        this.listingPrice = listingPrice;
        this.otherUserUid = otherUserUid;
        this.otherUserName = otherUserName;
        this.otherUserEmail = otherUserEmail;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public String getConvUid() {
        return convUid;
    }

    public void setConvUid(String convUid) {
        this.convUid = convUid;
    }

    public String getListingUid() {
        return listingUid;
    }

    public void setListingUid(String listingUid) {
        this.listingUid = listingUid;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public int getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(int listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getOtherUserUid() {
        return otherUserUid;
    }

    public void setOtherUserUid(String otherUserUid) {
        this.otherUserUid = otherUserUid;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public String getOtherUserEmail() {
        return otherUserEmail;
    }

    public void setOtherUserEmail(String otherUserEmail) {
        this.otherUserEmail = otherUserEmail;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
