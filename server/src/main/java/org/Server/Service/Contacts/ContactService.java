package org.Server.Service.Contacts;

import Model.DTO.BlockedContactDTO;
import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import Model.DTO.ParticipantDto;
import org.Server.Repository.ChatRepository;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.Contact;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Repository.ContactsRepository;
import org.Server.Service.Chat.ChatServices;
import org.Server.Service.User.UserService;
import org.Server.Service.UserSession;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactService {
    private final InvitationService invitationService;
    private final ContactsRepository contactsRepository;

    private final BlockedContactsService blockedContactsService;
    //private final User loggedUser;
    private final UserService userService;
    private final ChatServices chatServices;


    public ContactService() {
        this.invitationService = new InvitationService();
        this.contactsRepository = ContactsRepository.getInstance();
        //this.loggedUser = UserSession.getCurrentUser();
        this.userService = UserService.getInstance();
        chatServices = ChatServices.getInstance();
        try {
            this.blockedContactsService = new BlockedContactsService();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptInvitation(int userId, Integer acceptedUserID) {

        try {

            invitationService.deleteInvitation(acceptedUserID, userId);
            Contact contact = new Contact(userId, acceptedUserID, new Timestamp(System.currentTimeMillis()));
            contactsRepository.save(contact);

            chatServices.createPrivateChat(acceptedUserID, userId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelInvitation(String canceledUserPhoneNumber, Integer userId) {
        User canceledUser;
        canceledUser = userService.existsByPhoneNumber(canceledUserPhoneNumber);
        invitationService.deleteInvitation(canceledUser.getUserID(), userId);
    }

    public boolean addContact(int loggedUser, String phone) {
        if(blockedContactsService.existsByDTO(new BlockedContactDTO(loggedUser, phone))==-1)
            return invitationService.sendInvitation(loggedUser, phone);
        return false;
    }

    public void deleteContact(Integer userId, Integer contactId) {
        Contact contact = new Contact(userId, contactId);
        try {
            contactsRepository.deleteByEntity(contact);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ContactDto> getContacts(int userId) {
        List<Integer> contact_ids = null;
        contact_ids = contactsRepository.getContacts(userId);

        List<ContactDto> contacts = new ArrayList<>();
        for (Integer id : contact_ids) {
            try {
                User user = new UserRepository().findById(id);
                Integer chatID = new ChatRepository().getChatID(userId, id);
                contacts.add(mapUserToContactDto(user,chatID));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        for(ContactDto contactDto : contacts ){
            System.out.println(contactDto.getContactName());
        }
        return contacts;
    }

    public ContactDto mapUserToContactDto(User user,Integer chatId) {
        ContactDto contactDto = new ContactDto();
        contactDto.setContactID(user.getUserID());
        contactDto.setContactImage(user.getProfilePicture());
        contactDto.setContactName(user.getDisplayName());
        if (user.getBio() != null)
            contactDto.setBio(user.getBio());
        contactDto.setStatus(user.getUserStatus());
        contactDto.setMode(user.getUserMode());
        contactDto.setChatID(chatId);

        // Continue setting other properties as needed

        return contactDto;
    }


    public ChatDto createNewGroup(Integer adminID, List<Integer> participants,String grpName, byte[] grpImage) {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ChatDto chatDto = new ChatDto(grpName, grpImage, adminID, current, current);
        System.out.println("contactService : "+ chatDto.getAdminID());
        Integer chatID =  chatServices.createNewChat(
                chatDto,
                participants
        );
        List<ParticipantDto>participantsDTO =chatServices.mapToParticipantDTO(participants);

        chatDto.setChatID(chatID);
        chatDto.setParticipants(new ArrayList<>(participantsDTO));
        return chatDto;
    }

}
