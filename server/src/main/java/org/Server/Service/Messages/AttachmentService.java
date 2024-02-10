package org.Server.Service.Messages;

import Model.DTO.AttachmentDto;
import Model.DTO.MessageDTO;
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

    public Integer sendAttachment(AttachmentDto fileDto) {
        try {

            Integer messageId = messageService.sendMessage(new MessageDTO(fileDto.getChatID(), fileDto.getContent(), 1, fileDto.getSenderId()));
            Attachment attachment = new Attachment(messageId, fileDto.getFile());
            System.out.println(
                    "MessageId" + messageId
            );
            System.out.println(attachment.getMessageID());
            return attachmentReopsitory.save(attachment);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadAttachment(Integer attachmentID) {
        try {
            Attachment attachment = attachmentReopsitory.findById(attachmentID);
            System.out.println("the attachment size is " + attachment.getAttachment().length);
            return attachment.getAttachment();
        } catch (Exception e) {
            e.printStackTrace();
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
