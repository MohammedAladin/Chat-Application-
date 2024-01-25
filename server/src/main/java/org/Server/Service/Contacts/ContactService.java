package org.Server.Service.Contacts;

import Model.Entities.Contact;
import Model.Entities.User;
import Model.Entities.UserNotification;
import org.Server.Repository.ContactsRepository;
import org.Server.Service.User.UserService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
             acceptedUser = userService.existsById(acceptedUserPhoneNumber);
             if(acceptedUser!=null){
                 invitationService.deleteInvitation(acceptedUser.getUserID());

//                 Contact contact = new Contact(loggedUser.getUserID(),acceptedUser.getUserID(),new Timestamp(System.currentTimeMillis()));
//                 contactsRepository.save(contact);
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void cancelContact(String canceledUserPhoneNumber){
        User canceledUser;
        try {
            canceledUser = userService.existsById(canceledUserPhoneNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        invitationService.deleteInvitation(canceledUser.getUserID());
    }

}
