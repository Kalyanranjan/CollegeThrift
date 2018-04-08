package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by kal on 3/25/18.
 */

@IgnoreExtraProperties
public class Message {

    public String sender1Uid;
    public String sender2Uid;
    public String sentByUid;
    public String messageText;
    public long MessageTime;

    public Message() {
    }

    public Message(String sender1Uid, String sender2Uid, String sentByUid, String messageText, long messageTime) {
        this.sender1Uid = sender1Uid;
        this.sender2Uid = sender2Uid;
        this.sentByUid = sentByUid;
        this.messageText = messageText;
        MessageTime = messageTime;
    }

    public String getSender1Uid() {
        return sender1Uid;
    }

    public void setSender1Uid(String sender1Uid) {
        this.sender1Uid = sender1Uid;
    }

    public String getSender2Uid() {
        return sender2Uid;
    }

    public void setSender2Uid(String sender2Uid) {
        this.sender2Uid = sender2Uid;
    }

    public String getSentByUid() {
        return sentByUid;
    }

    public void setSentByUid(String sentByUid) {
        this.sentByUid = sentByUid;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(long messageTime) {
        MessageTime = messageTime;
    }
}
