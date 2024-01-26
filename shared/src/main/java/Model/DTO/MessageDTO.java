package Model.DTO;

public class MessageDTO {
    private String phoneNumber;
    private String content;
    private Integer isAttachment;

    public MessageDTO(String phoneNumber, String content, Integer isAttachment) {
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.isAttachment = isAttachment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
