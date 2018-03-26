package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by kal on 3/26/18.
 */

@IgnoreExtraProperties
public class Conversation {

    private String otherPersonUid;
    private HashMap<String, String> messages;
    private String lastMessage;

    public Conversation() {
    }

    public Conversation(String otherPersonUid, HashMap<String, String> messages, String lastMessage) {
        this.otherPersonUid = otherPersonUid;
        this.messages = messages;
        this.lastMessage = lastMessage;
    }

    public String getOtherPersonUid() {
        return otherPersonUid;
    }

    public void setOtherPersonUid(String otherPersonUid) {
        this.otherPersonUid = otherPersonUid;
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, String> messages) {
        this.messages = messages;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
