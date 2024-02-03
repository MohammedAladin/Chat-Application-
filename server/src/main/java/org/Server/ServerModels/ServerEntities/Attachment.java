package org.Server.ServerModels.ServerEntities;

public class Attachment {
    private Integer attachmentID;
    private Integer messageID;
    private byte[] attachment;

    public Attachment(Integer messageID, byte[] attachment) {

        this.messageID = messageID;
        this.attachment = attachment;
    }

    public Attachment() {
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
