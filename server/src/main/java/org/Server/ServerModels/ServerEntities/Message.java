package org.Server.ServerModels.ServerEntities;

import java.sql.Timestamp;

public class Message {
    private int messageID;
    private int senderID;
    private int receiverID;
    private String messageContent;
    private Timestamp messageTimestamp;
    private boolean isAttachment;

    public Message(int senderID, int receiverID, String messageContent, Timestamp messageTimestamp, boolean isAttachment) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.messageContent = messageContent;
        this.messageTimestamp = messageTimestamp;
        this.isAttachment = isAttachment;
    }

    public Message() {
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(Timestamp messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public boolean isAttachment() {
        return isAttachment;
    }

    public void setAttachment(boolean attachment) {
        isAttachment = attachment;
    }
}
