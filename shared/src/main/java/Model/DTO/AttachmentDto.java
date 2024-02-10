package Model.DTO;

import java.io.File;
import java.io.Serializable;

public class AttachmentDto implements Serializable {
    Integer chatID;
    Integer senderId;
    byte [] file;
    String content;

    public AttachmentDto(Integer chatID, Integer senderId, byte[] file, String content) {
        this.chatID = chatID;
        this.senderId = senderId;
        this.file = file;
        this.content = content;
    }

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
