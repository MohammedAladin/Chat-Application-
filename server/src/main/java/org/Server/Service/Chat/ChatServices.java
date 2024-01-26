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
    private ChatServices(){
        try {
            userService = new UserService();
            chatRepository = new ChatRepository();
            chatParticipantServices = ChatParticipantServices.getInstance();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public static ChatServices getInstance(){
        if(chatServices ==null){
            chatServices = new ChatServices();
        }
        return chatServices;
    }
    public void createNewChat(ChatDto chatDto, List<String> participantsPhoneNumber){
        if(chatDto.getAdminID()==null){ //private chat

        }
        else{ //group
            chatRepository.save(mapToChat(chatDto));

            List<User> users =  participantsPhoneNumber.stream().
                    map((phone)->userService.existsByPhoneNumber(phone)).collect(Collectors.toList());
            try {
                chatParticipantServices.addParticipants(chatRepository.findByName(chatDto.getChatName()).getChatID(), users);
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
