package Model.DTO;

public class MessageDTO {
    private Integer chatID;
    private String content;
    private Integer isAttachment;
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
