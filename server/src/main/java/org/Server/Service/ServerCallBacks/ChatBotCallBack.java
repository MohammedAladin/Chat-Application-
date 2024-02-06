package org.Server.Service.ServerCallBacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBotCallBack {


    private static ChatBotCallBack chatBotCallBack;
    Map<Integer, List<Integer>> chatBotParticipantsByChatId;
    private ChatBotCallBack(){
        chatBotParticipantsByChatId = new HashMap<>();
    }
    public static ChatBotCallBack getInstance(){
        if(chatBotCallBack==null){
            chatBotCallBack = new ChatBotCallBack();
        }
        return chatBotCallBack;
    }

    public void registerForChatBot(Integer participantId, Integer chatId){
        List<Integer> participants = chatBotParticipantsByChatId
                .computeIfAbsent(chatId, k -> new ArrayList<>());
        participants.add(participantId);
    }
    public void unregisterFromChatBot(Integer participantId, Integer chatId) {
        List<Integer> participants = chatBotParticipantsByChatId.get(chatId);
        if (participants != null) {
            participants.remove(participantId);

            if (participants.isEmpty()) {
                chatBotParticipantsByChatId.remove(chatId);
            }
        }
    }
    public List<Integer> getParticipantsForSpecificChat(Integer chatId){
        if(chatBotParticipantsByChatId.containsKey(chatId)){
            return chatBotParticipantsByChatId.get(chatId);
        }
        return new ArrayList<>();
    }
}
