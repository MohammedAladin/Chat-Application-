package Model.Entities;

import java.util.Date;

public class Notification {
    private int notificationID;
    private int userID;
    private String notificationType;
    private int senderID;
    private String notificationContent;
    private Date timestamp;
    private String status;

    // Constructors
    public Notification() {}

    public Notification(int userID, String notificationType, int senderID, String notificationContent, String status) {
        this.userID = userID;
        this.notificationType = notificationType;
        this.senderID = senderID;
        this.notificationContent = notificationContent;
        this.status = status;
    }

    // Getters and Setters
    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
