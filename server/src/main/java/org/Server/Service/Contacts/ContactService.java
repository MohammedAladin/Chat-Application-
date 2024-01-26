package org.Server.Service.Contacts;

import org.Server.ServerModels.ServerEntities.Contact;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Repository.ContactsRepository;
import org.Server.Service.User.UserService;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ContactService {
    private final InvitationService invitationService;
    private final ContactsRepository contactsRepository;
    private final User loggedUser;
    private final UserService userService;




    public ContactService(InvitationService invitationService, ContactsRepository contactsRepository,
                           User loggedUser, UserService userService){
        this.invitationService = invitationService;
        this.contactsRepository = contactsRepository;
        this.loggedUser = loggedUser;
        this.userService = userService;
    }
    public void addContact(String phone){
        invitationService.sendInvitation(loggedUser, phone);
    }
    public void acceptContact(String acceptedUserPhoneNumber){
        User acceptedUser;
        try {
             acceptedUser = userService.existsByPhoneNumber(acceptedUserPhoneNumber);
             if(acceptedUser!=null){
//                 invitationService.deleteInvitation(acceptedUser.getUserID());

                 Contact contact = new Contact(loggedUser.getUserID(),acceptedUser.getUserID(),new Timestamp(System.currentTimeMillis()));
                 contactsRepository.save(contact);
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void cancelContact(String canceledUserPhoneNumber){
        User canceledUser;
        canceledUser = userService.existsByPhoneNumber(canceledUserPhoneNumber);
        invitationService.deleteInvitation(canceledUser.getUserID());
    }

}
