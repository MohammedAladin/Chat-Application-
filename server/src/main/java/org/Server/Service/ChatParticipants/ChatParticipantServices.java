package org.Server.Service.ChatParticipants;

import org.Server.Repository.ChatParticipantRepository;
import org.Server.ServerModels.ServerEntities.ChatParticipants;
import org.Server.ServerModels.ServerEntities.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ChatParticipantServices {
    private static ChatParticipantServices chatParticipantServices;
    private final ChatParticipantRepository chatParticipantRepository;
    private ChatParticipantServices(){
        chatParticipantRepository = new ChatParticipantRepository();
    }
    public static ChatParticipantServices getInstance(){
        if(chatParticipantServices==null){
            chatParticipantServices = new ChatParticipantServices();
        }
        return chatParticipantServices;
    }
    public void addParticipants(int chatId, List<User> users){
        for(User user : users){
            try {
                ChatParticipants chatParticipants = new ChatParticipants(chatId, user.getUserID(), new Timestamp(System.currentTimeMillis()));
                chatParticipantRepository.save(chatParticipants);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
