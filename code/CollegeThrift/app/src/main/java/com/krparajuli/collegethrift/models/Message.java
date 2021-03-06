package com.krparajuli.collegethrift.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by kal on 3/25/18.
 */

@IgnoreExtraProperties
public class Message {

    public String messageSenderUid;
    public String messageReceiverUid;
    public String messageText;
    public long messageTime;

    public Message() {
    }

    public Message(String messageSenderUid, String messageReceiverUid, String messageText, long messageTime) {
        this.messageSenderUid = messageSenderUid;
        this.messageReceiverUid = messageReceiverUid;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public String getSenderUid() {
        return messageSenderUid;
    }

    public void setSenderUid(String senderUid) {
        this.messageSenderUid = senderUid;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageReceiverUid() {
        return messageReceiverUid;
    }

    public void setMessageReceiverUid(String messageReceiverUid) {
        this.messageReceiverUid = messageReceiverUid;
    }
}
