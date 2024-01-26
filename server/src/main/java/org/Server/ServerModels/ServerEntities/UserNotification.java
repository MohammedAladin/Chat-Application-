package org.Server.ServerModels.ServerEntities;
import java.sql.Timestamp;
public class UserNotification {
    private int notificationID;
    private int receiverID;
    private int senderID;
    private String notificationMessage;
    private Timestamp notificationSentDate;

    public UserNotification(int senderID, int receiverID, String notificationMessage, Timestamp notificationSentDate) {
        this.receiverID = receiverID;
        this.senderID = senderID;
        this.notificationMessage = notificationMessage;
        this.notificationSentDate = notificationSentDate;
    }

    public UserNotification() {

    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Timestamp getNotificationSentDate() {
        return notificationSentDate;
    }

    public void setNotificationSentDate(Timestamp notificationSentDate) {
        this.notificationSentDate = notificationSentDate;
    }
}
