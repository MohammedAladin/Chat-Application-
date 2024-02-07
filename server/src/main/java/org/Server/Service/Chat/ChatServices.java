package org.Server.Service.Chat;
import Model.DTO.ChatDto;
import Model.DTO.ParticipantDto;
import org.Server.Repository.ChatRepository;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Service.ChatParticipants.ChatParticipantServices;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public Integer createNewChat(ChatDto chatDto, List<Integer> participantsIds) {
        chatRepository.saveGroup(mapToChat(chatDto));
        try {
            int chatId = chatRepository.findByName(chatDto.getChatName()).getChatID();
            chatParticipantServices.addParticipants(
                    chatId,
                    participantsIds
            );
            return chatId;
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
    public ArrayList<ChatDto> getGroupChats(int userId){
        ArrayList<Chat> chats = new ArrayList<>(chatRepository.getGroupChats(userId));
        ArrayList<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat:chats){
            chatDtos.add(mapToChatDto(chat));
        }
        return chatDtos;
    }

    private ChatDto mapToChatDto(Chat chat) {
        return new ChatDto(
                chat.getChatName()+"",
                chat.getChatImage(),
                chat.getAdminID(),
                chat.getCreationDate(),
                chat.getLastModified(),
                chat.getChatID(),
                new ArrayList<>(mapToParticipantDTO(chatRepository.getAllParticipants(chat.getChatID())))
        );
    }

    public List<ParticipantDto> mapToParticipantDTO(List<Integer> allParticipants) {
        List<ParticipantDto> participantDtos = new ArrayList<>();
        for (Integer id:allParticipants){
            String name;
            byte [] image;
            try { User user = new UserRepository().findById(id);
                 name=user.getDisplayName();
                 image=user.getProfilePicture();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            participantDtos.add(new ParticipantDto(id,name,image));
        }
        return participantDtos;

    }


}
