package Model.Entities;

import java.util.Date;

public class ServerAnnouncement {
    private int announcementID;
    private String announcementContent;
    private Date timestamp;

    // Constructors
    public ServerAnnouncement() {}

    public ServerAnnouncement(String announcementContent) {
        this.announcementContent = announcementContent;
    }

    // Getters and Setters
    public int getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(int announcementID) {
        this.announcementID = announcementID;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}