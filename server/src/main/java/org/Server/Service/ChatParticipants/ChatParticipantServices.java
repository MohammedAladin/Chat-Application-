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
    public void addParticipants(int chatId, List<Integer> usersIds){
        for(Integer userId : usersIds){
            try {
                ChatParticipants chatParticipants = new ChatParticipants(chatId, userId, new Timestamp(System.currentTimeMillis()));
                chatParticipantRepository.save(chatParticipants);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Integer ifParticipantsExisted(Integer userId1, Integer userId2){
        System.out.println("ifParticipantsExisted.....");
        return chatParticipantRepository.getChatIDForParticipants(userId1,userId2);
    }
}
