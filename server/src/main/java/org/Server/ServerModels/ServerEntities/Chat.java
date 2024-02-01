package org.Server.ServerModels.ServerEntities;



import java.sql.Timestamp;

public class Chat {
    private Integer chatID;
    private String chatName;
    private byte[] chatImage;
    private Integer adminID;
    private Timestamp creationDate;
    private Timestamp lastModified;

    public Chat( String chatName, byte[] chatImage, Integer adminID, Timestamp creationDate, Timestamp lastModified) {

        this.chatName = chatName;
        this.chatImage = chatImage;
        this.adminID = adminID;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
    }

    public Chat() {
    }

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public byte[] getChatImage() {
        return chatImage;
    }

    public void setChatImage(byte[] chatImage) {
        this.chatImage = chatImage;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
