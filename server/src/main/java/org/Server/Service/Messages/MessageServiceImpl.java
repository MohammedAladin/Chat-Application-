package org.Server.Service.Messages;

import Model.DTO.AttachmentDto;
import Model.DTO.MessageDTO;
import org.Server.Repository.AttachmentReopsitory;
import org.Server.Repository.MessageRepository;
import org.Server.ServerModels.ServerEntities.Attachment;
import org.Server.ServerModels.ServerEntities.Message;
import org.Server.Service.Chat.ChatServices;
import org.Server.Service.UserSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageServiceImpl {
    MessageRepository messageRepository;
    AttachmentReopsitory attachmentReopsitory;
    static MessageServiceImpl messageServiceImpl;


    private final ChatServices chatServices;
    private MessageServiceImpl() throws RemoteException {
        super();
        messageRepository = new MessageRepository();
        chatServices = ChatServices.getInstance();
        attachmentReopsitory = AttachmentReopsitory.getInstance();
    }

    public static MessageServiceImpl getInstance (){
        if (messageServiceImpl == null) {
            try {
                messageServiceImpl = new MessageServiceImpl();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return messageServiceImpl;
    }
    public void sendMessage(MessageDTO messageDTO) {
        Message message = messageDTOToMessage(messageDTO);
        messageRepository.save(message);
    }
    public void sendAttachment(AttachmentDto fileDto){
        try {
            Message message = new Message(
                    fileDto.getSenderId(),
                    fileDto.getChatID(),
                    fileDto.getContent(),
                    new Timestamp(System.currentTimeMillis()),
                    true);
            messageRepository.save(message);

            Integer messageId = messageRepository.getLastInsertedId();

            byte[] fileBytes = readFileToBytes(fileDto.getFile());
            Attachment attachment = new Attachment(messageId,fileBytes);
            attachmentReopsitory.save(attachment);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private byte[] readFileToBytes(File file) throws Exception {
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != file.length()) {
                throw new Exception("Error reading file into byte array");
            }
            return buffer;
        }
    }
    private Message messageDTOToMessage (MessageDTO messageDTO){
        return new Message (
                messageDTO.getSenderID(),
                messageDTO.getChatID(),
                messageDTO.getContent(),
                new Timestamp(System.currentTimeMillis()),
                messageDTO.getIsAttachment() == 1
        );
    }

    public List<MessageDTO> getPrivateChatMessages(Integer chatID){
        List<Message> list =new MessageRepository().getPrivateChatMessages(chatID);
        ArrayList<MessageDTO> messageDTOS=new ArrayList<>();
        for (Message message : list) {
            messageDTOS.add(mapToMessageDTO(message));
        }
        return messageDTOS;
    }

    private MessageDTO mapToMessageDTO(Message message) {
        return new MessageDTO(
                message.getReceiverID(),
                message.getMessageContent(),
                message.isAttachment() ? 1 : 0,
                message.getSenderID()
        );
    }


}
