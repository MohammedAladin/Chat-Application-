package org.Server.Service.Contacts;

import Model.DTO.ChatDto;
import org.Server.ServerModels.ServerEntities.Contact;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Repository.ContactsRepository;
import org.Server.Service.Chat.ChatServices;
import org.Server.Service.User.UserService;
import org.Server.Service.UserSession;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactService {
    private final InvitationService invitationService;
    private final ContactsRepository contactsRepository;
    private final User loggedUser;
    private final UserService userService;
    private final ChatServices chatServices;



    public ContactService(){
        this.invitationService = new InvitationService();
        this.contactsRepository = ContactsRepository.getInstance();
        this.loggedUser = UserSession.getCurrentUser();
        this.userService = UserService.getInstance();
        chatServices = ChatServices.getInstance();
    }
    private void createNewChat(User acceptedUser){
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ChatDto chatDto = new ChatDto(null,null,null, current, current);

        int user1Id = UserSession.getCurrentUser().getUserID();
        int user2Id = acceptedUser.getUserID();

        List<Integer> ids = new ArrayList<>();
        ids.add(user1Id); ids.add(user2Id);

        chatServices.createNewChat(
                chatDto,
                ids
        );
    }
    public void acceptInvitation(String acceptedUserPhoneNumber){
        User acceptedUser;
        try {
             acceptedUser = userService.existsByPhoneNumber(acceptedUserPhoneNumber);
             if(acceptedUser!=null){
                 invitationService.deleteInvitation(acceptedUser.getUserID());
                 Contact contact = new Contact(loggedUser.getUserID(),acceptedUser.getUserID(),new Timestamp(System.currentTimeMillis()));
                 contactsRepository.save(contact);

                 createNewChat(acceptedUser);
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void cancelInvitation(String canceledUserPhoneNumber){
        User canceledUser;
        canceledUser = userService.existsByPhoneNumber(canceledUserPhoneNumber);
        invitationService.deleteInvitation(canceledUser.getUserID());
    }
    public void addContact(String phone){
        invitationService.sendInvitation(loggedUser, phone);
    }

    public void deleteContact(){

    }

}
