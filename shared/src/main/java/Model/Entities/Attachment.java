package Model.Entities;

public class Attachment {
    private int attachmentID;
    private int messageID;
    private byte[] attachment;

    public Attachment(int attachmentID, int messageID, byte[] attachment) {
        this.attachmentID = attachmentID;
        this.messageID = messageID;
        this.attachment = attachment;
    }

    public int getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(int attachmentID) {
        this.attachmentID = attachmentID;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
