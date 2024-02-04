package Model.DTO;

import java.io.Serializable;
import java.sql.Timestamp;

public class NotificationDTO implements Serializable {
    private Integer notificationID;
    private Integer userID;
    private Integer senderID;
    private String notificationContent;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    private String senderName;



    // Constructors
    public NotificationDTO() {}

    public NotificationDTO(int userID, String notificationType, int senderID, String notificationContent,String senderName) {
        this.userID = userID;
        this.senderID = senderID;
        this.notificationContent = notificationContent;
        this.senderName = senderName;

    }

    // Getters and Setters
    public Integer getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public Integer getSenderID() {
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


}
