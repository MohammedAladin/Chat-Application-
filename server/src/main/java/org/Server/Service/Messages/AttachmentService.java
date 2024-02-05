package org.Server.Service.Messages;

import Model.DTO.AttachmentDto;
import org.Server.RepoInterfaces.AttachmentRepoInterface;
import org.Server.Repository.AttachmentReopsitory;
import org.Server.ServerModels.ServerEntities.Attachment;
import org.Server.ServerModels.ServerEntities.Message;

import java.io.*;
import java.sql.Timestamp;

public class AttachmentService {
    private final AttachmentRepoInterface attachmentReopsitory;
    private final MessageServiceImpl messageService = MessageServiceImpl.getInstance();
    private static AttachmentService attachmentService;

    private AttachmentService() {
        this.attachmentReopsitory = AttachmentReopsitory.getInstance();
    }

    public static AttachmentService getInstance() {
        if (attachmentService == null) {
            attachmentService = new AttachmentService();
        }
        return attachmentService;
    }

    public void sendAttachment(AttachmentDto fileDto) {
        try {
            Message message = new Message(
                    fileDto.getSenderId(),
                    fileDto.getChatID(),
                    fileDto.getContent(),
                    new Timestamp(System.currentTimeMillis()),
                    true);

            Integer messageId = messageService.getLastId();

            byte[] fileBytes = readFileToBytes(fileDto.getFile());
            Attachment attachment = new Attachment(messageId, fileBytes);
            attachmentReopsitory.save(attachment);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadAttachment(Integer messageID) {
        try {
            Attachment attachment = attachmentReopsitory.findByMessageId(messageID);
            return attachment.getAttachment();
        } catch (Exception e) {
            throw new RuntimeException("Error downloading attachment", e);
        }
    }

    private byte[] readFileToBytes(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != file.length()) {
                throw new IOException("Error reading file into byte array");
            }
            return buffer;
        }
    }
}
