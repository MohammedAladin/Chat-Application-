package org.Server.Service.Chat;
import Model.DTO.ChatDto;
import org.Server.Repository.ChatRepository;
import org.Server.ServerModels.ServerEntities.Chat;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Service.ChatParticipants.ChatParticipantServices;
import org.Server.Service.User.UserService;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class ChatServices {
    private static ChatServices chatServices;
    private ChatRepository chatRepository;
    private UserService userService;
    private ChatParticipantServices chatParticipantServices;
    private ChatServices() throws RemoteException {
        userService = UserService.getInstance();
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
    public void createNewChat(ChatDto chatDto, List<User> participants){
        if(chatDto.getAdminID()==null){ //private chat

        }
        else{ //group
            chatRepository.save(mapToChat(chatDto));
            try {
                int chatId = getChatId(chatRepository.findByName(chatDto.getChatName()).getChatName());
                chatParticipantServices.addParticipants(
                        chatId,
                        participants
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
