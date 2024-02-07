package org.Server.Service.Chat;
import Model.DTO.MessageDTO;
import com.google.code.chatterbotapi.*;
import org.Server.Service.Messages.MessageServiceImpl;

import java.sql.Timestamp;

public class ChatBot {
    private static ChatterBotSession replierBot;
    private MessageServiceImpl messageService;
    public ChatBot(){
        try {
            ChatterBotFactory factory = new ChatterBotFactory();
            ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            replierBot = bot.createSession();

            messageService = MessageServiceImpl.getInstance();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public  MessageDTO replyUsingChatBot(MessageDTO message, Integer senderId){
        try {
            String reply = replierBot.think(message.getContent());
            MessageDTO autoCreatedMessage= new MessageDTO(
                    message.getChatID(),
                    reply,
                    0,
                    senderId,
                    Timestamp.valueOf(java.time.LocalDateTime.now())

            );

            messageService.sendMessage(autoCreatedMessage);

            return autoCreatedMessage;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
