package org.Server.Service.Chat;
import Model.DTO.ChatDto;
import org.Server.Repository.ChatRepository;
import org.Server.ServerModels.ServerEntities.Chat;
import org.Server.Service.ChatParticipants.ChatParticipantServices;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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
    public void createNewChat(ChatDto chatDto, List<Integer> participantsIds){
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
    public void getAllChats(){

    }
    public int getChatId(String chatName){
        try {
            return chatRepository.findByName(chatName).getChatID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Chat mapToChat(ChatDto dto){
        return new Chat(
                dto.getChatName(),
                dto.getChatImage(),
                dto.getAdminID(),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }


}
