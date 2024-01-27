package org.Server;
import Model.DTO.ChatDto;
import Model.DTO.MessageDTO;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Service.Chat.ChatServices;

import org.Server.Service.Messages.MessageServiceImpl;
import org.Server.Service.User.UserService;
import org.Server.Service.UserSession;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestApp {
    public static void main(String[] args) {

        User user1 = new User(
                "123456789",
                "John Doe",
                "john@example.com",
                null,
                "hashed_password",
                "Male",
                "USA",
                Date.valueOf("2000-01-01"),
                "Bio goes here",
                "Available",
                "Online",
                Timestamp.valueOf("2022-01-01 12:00:00")
        );
        User user2 = new User(
                "987654321",
                "Jane Smith",
                "jane@example.com",
                null,
                "hashed_password",
                "Female",
                "Canada",
                Date.valueOf("1995-05-15"),
                "Another bio",
                "Busy",
                "Offline",
                Timestamp.valueOf("2022-01-02 10:30:00")
        );

        try {
            UserService userService = UserService.getInstance();
//            userService.registerUser(user1);
//            userService.registerUser(user2);

            UserSession.setCurrentUser(userService.existsByPhoneNumber(user1.getPhoneNumber()));
            System.out.println("Logged Userid " + userService.existsByPhoneNumber(user1.getPhoneNumber()).getUserID());
            System.out.println("User To Be get message " + userService.existsByPhoneNumber(user2.getPhoneNumber()).getUserID());

            ChatServices chatServices = ChatServices.getInstance();

            List<User> users = new ArrayList<>();
            users.add(user1); users.add(user2);
            int id = userService.existsByPhoneNumber(user1.getPhoneNumber()).getUserID();

            chatServices.createNewChat(
                    new ChatDto("Amin", null, id, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())),
                    users
            );

            MessageServiceImpl messageService = MessageServiceImpl.getInstance();

            messageService.sendMessage(new MessageDTO("Amin", "Hello..", 0));

            System.out.println("Message has been sent... ");


//
//            InvitationService invitationService = new InvitationService(
//                    userService,
//                    new UserNotificationRepository(connectionManager.getMyConnection())
//            );
//
//            ContactService contactService = new ContactService(
//                    invitationService,
//                    new ContactsRepository(connectionManager.getMyConnection()),
//                    UserSession.getCurrentUser(),
//                    userService
//            );
//
//            contactService.acceptContact(user1.getPhoneNumber());

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


}
