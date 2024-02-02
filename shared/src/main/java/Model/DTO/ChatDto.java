package Model.DTO;

import java.io.Serializable;
import java.sql.Timestamp;

public class ChatDto implements Serializable {
    private String chatName;
    private byte[] chatImage;
    private Integer adminID;
    private Timestamp creationDate;
    private Timestamp lastModified;

    public ChatDto(String chatName, byte[] chatImage, Integer adminID, Timestamp creationDate, Timestamp lastModified, Integer chatID) {
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.adminID = adminID;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.chatID = chatID;
    }

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    private Integer chatID;

    public ChatDto( String chatName, byte[] chatImage, Integer adminID, Timestamp creationDate, Timestamp lastModified) {
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.adminID = adminID;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
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
