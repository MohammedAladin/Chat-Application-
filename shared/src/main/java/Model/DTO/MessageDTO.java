package Model.DTO;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDTO implements Serializable {
    private Integer chatID;
    private String content;
    private Integer isAttachment;
    private Integer senderID;

    public MessageDTO(Integer chatID, String content, Integer isAttachment, Integer senderID) {
        this.chatID = chatID;
        this.content = content;
        this.isAttachment = isAttachment;
        this.senderID = senderID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private Timestamp timestamp;



    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }

    public MessageDTO(Integer chatID, String content, Integer isAttachment, Integer senderID, Timestamp timestamp) {
        this.chatID = chatID;
        this.content = content;
        this.isAttachment = isAttachment;
        this.senderID = senderID;
        this.timestamp = timestamp;
    }


    public MessageDTO(Integer chatID, String content, Integer isAttachment) {
        this.chatID = chatID;
        this.content = content;
        this.isAttachment = isAttachment;
    }

    public Integer getChatID() {
        return chatID;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(Integer isAttachment) {
        this.isAttachment = isAttachment;
    }
}
