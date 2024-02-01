package org.Server.Service.Chat;
import Model.DTO.ChatDto;
import org.Server.Repository.ChatRepository;
import org.Server.Service.ChatParticipants.ChatParticipantServices;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.Server.ServerModels.ServerEntities.Chat;

public class ChatServices {
    private static ChatServices chatServices;
    private final ChatRepository chatRepository;
    private final ChatParticipantServices chatParticipantServices;
    private ChatServices() throws RemoteException {
        chatRepository = new ChatRepository();
        chatParticipantServices = ChatParticipantServices.getInstance();
    }

    public static ChatServices getInstance(){
        if(chatServices ==null){
            try {
                chatServices = new ChatServices();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return chatServices;
    }
    public void createNewChat(ChatDto chatDto, List<Integer> participantsIds) {
        chatRepository.save(mapToChat(chatDto));
        try {
            int chatId = chatRepository.findByName(chatDto.getChatName()).getChatID();
            chatParticipantServices.addParticipants(
                    chatId,
                    participantsIds
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createPrivateChat(Integer user1,Integer user2){
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ChatDto chatDto = new ChatDto(user1+user2+"", null, null, current, current);
        List<Integer> ids = List.of(user1,user2);
        chatRepository.save(mapToChat(chatDto));
        try {
            int chatId = chatRepository.findByName(chatDto.getChatName()).getChatID();
            chatParticipantServices.addParticipants(
                    chatId,
                    ids
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Integer> getAllParticipants(Integer chatID){
        return chatRepository.getAllParticipants(chatID);
    }
    public int getChatId(String chatName){
        try {
            return chatRepository.findByName(chatName).getChatID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Chat mapToChat(ChatDto dto) {
        if (dto == null) {
            System.out.println("ChatServices.mapToChat: dto is null");
        }

        // Can be null
        String ChatName=dto.getChatName();
        byte [] ChatImage =dto.getChatImage();
        Integer adminID = dto.getAdminID();
        if (ChatImage==null){
            ChatImage = new byte[0];
        }

        return new Chat(
                ChatName,
                ChatImage,
                adminID,  // Use the possibly null adminID directly
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }



}
