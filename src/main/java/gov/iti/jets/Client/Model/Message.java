package gov.iti.jets.Client.Model;

import java.util.Date;

public class Message {
    private int messageID;
    private int senderID;
    private int receiverID;
    private int groupID;
    private String messageContent;
    private Date timestamp;
    private String messageStatus;
    private String messageType;

    // Constructors
    public Message() {}

    public Message(int senderID, int receiverID, int groupID, String messageContent,
                   String messageStatus, String messageType) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.groupID = groupID;
        this.messageContent = messageContent;
        this.messageStatus = messageStatus;
        this.messageType = messageType;
    }

    // Getters and Setters
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

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
