package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by kal on 3/26/18.
 */

@IgnoreExtraProperties
public class Conversation {

    private String listingUid;
    private String otherUserUid;
    private String lastMessage;
    private String lastMessageTime;

    public Conversation() {
    }

    public Conversation(String listingUid, String otherUserUid, String lastMessage, String lastMessageTime) {
        this.listingUid = listingUid;
        this.otherUserUid = otherUserUid;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public String getListingUid() {
        return listingUid;
    }

    public void setListingUid(String listingUid) {
        this.listingUid = listingUid;
    }

    public String getOtherUserUid() {
        return otherUserUid;
    }

    public void setOtherUserUid(String otherUserUid) {
        this.otherUserUid = otherUserUid;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
