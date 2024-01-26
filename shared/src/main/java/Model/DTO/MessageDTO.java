package Model.DTO;

public class MessageDTO {
    private String chatName;
    private String content;
    private Integer isAttachment;
    public MessageDTO(String chatName, String content, Integer isAttachment) {
        this.chatName = chatName;
        this.content = content;
        this.isAttachment = isAttachment;
    }
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
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
