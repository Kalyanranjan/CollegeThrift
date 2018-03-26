package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by kal on 3/25/18.
 */

@IgnoreExtraProperties
public class Message {

    private int messageOrder;
    private int timestamp;
    private short receivedOrSent;  // received = 0, sent = 1
    private String messageText;

    public Message() {}

    public Message(int messageOrder, int timestamp, short receivedOrSent, String messageText) {
        this.messageOrder = messageOrder;
        this.timestamp = timestamp;
        this.receivedOrSent = receivedOrSent;
        this.messageText = messageText;
    }

    public int getMessageOrder() {
        return messageOrder;
    }

    public void setMessageOrder(int messageOrder) {
        this.messageOrder = messageOrder;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public short getReceivedOrSent() {
        return receivedOrSent;
    }

    public void setReceivedOrSent(short receivedOrSent) {
        this.receivedOrSent = receivedOrSent;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
