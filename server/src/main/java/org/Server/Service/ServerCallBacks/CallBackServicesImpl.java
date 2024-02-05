package org.Server.Service.ServerCallBacks;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.*;
import SharedEnums.StatusEnum;
import com.mysql.cj.xdevapi.Client;
import javafx.application.Platform;
import org.Server.Repository.ContactsRepository;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.Attachment;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.ServerModels.ServerEntities.UserNotification;
import org.Server.Service.Chat.ChatServices;
import org.Server.Service.Contacts.ContactService;
import org.Server.Service.Contacts.InvitationService;
import org.Server.Service.Contacts.NotificationMapper;
import org.Server.Service.Messages.AttachmentService;
import org.Server.Service.Messages.MessageServiceImpl;
import org.Server.Service.User.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CallBackServicesImpl extends UnicastRemoteObject implements CallBackServicesServer {
    MessageServiceImpl messageService;
    ChatServices chatServices;
    UserService userService = UserService.getInstance();
    AttachmentService attachmentService = AttachmentService.getInstance();
    ContactService contactService = new ContactService();
    Map<Integer, CallBackServicesClient> clients = new HashMap<>();


    public void register(CallBackServicesClient client, String clientphone) throws RemoteException {
        User user =  userService.existsByPhoneNumber(clientphone);

        ArrayList<NotificationDTO> notificationDTOS = getNotificationList(user.getUserID());
        clients.put(user.getUserID(), client);
        client.setClientId(user.getUserID());

        System.out.println("Client registered : id = " + user.getUserID());
        UserRegistrationDTO userDTO = userService.toUserDto(user);
        client.setData(userDTO);

        try {
            client.setNotificationList(notificationDTOS);
            client.setContactList(new ContactService().getContacts(user.getUserID()));
            client.setGroupList(ChatServices.getInstance().getGroupChats(user.getUserID()));
            noyifyContacts(user.getUserID(),user.getDisplayName());
            changeStatus(user.getUserID(), "Online");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Client registered :id = " + user.getUserID());
    }

    private void noyifyContacts(int userID,String name) {
        List<Integer> contacts = ContactsRepository.getInstance().getContacts(userID);
        for (Integer contact : contacts) {
            if (!clients.containsKey(contact))
                continue;
            CallBackServicesClient contactClient = clients.get(contact);
            Platform.runLater(() -> {
                try {
                    contactClient.notifyClient(name+" is now online");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private ArrayList<NotificationDTO> getNotificationList(Integer clientId) {
        ArrayList<UserNotification> notifications = new ArrayList<>(new InvitationService().getInvitations(clientId));
        NotificationMapper notificationMapper = new NotificationMapper();
        return notificationMapper.mapToDTOList(notifications);
    }

    public void unRegister(Integer clientId , String phoneNumber) throws RemoteException {
        changeStatus(clientId, "Offline");
        clients.remove(clientId);


    }


    public CallBackServicesImpl() throws RemoteException {
        messageService = MessageServiceImpl.getInstance();
        chatServices = ChatServices.getInstance();
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) throws RemoteException {
        messageService.sendMessage(messageDTO);
        System.out.println("ChatId serverSide----> " + messageDTO.getChatID());
        List<Integer> chatParticipantsIds = chatServices.getAllParticipants(messageDTO.getChatID());
        for(Integer id : chatParticipantsIds){
            if(clients.containsKey(id)){
                CallBackServicesClient client = clients.get(id);
                Platform.runLater(() -> {
                    try {
                        client.receiveMessage(messageDTO);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
    @Override
    public void sendAttachment(AttachmentDto attachmentMessage) throws RemoteException {
        attachmentService.sendAttachment(attachmentMessage);
        List<Integer> chatParticipantsIds = chatServices.getAllParticipants(attachmentMessage.getChatID());

        List<CallBackServicesClient> selectedClients = clients.entrySet().stream()
                .filter(entry -> chatParticipantsIds.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        for (CallBackServicesClient client : selectedClients) {
                client.receiveAttachment(attachmentMessage);
            }

    }
    @Override
    public void updateProfile(Integer id, Map<String, String> updatedFields){
        userService.updateUserInfo(id, updatedFields);
        updateContactList(id);
    }
    @Override
    public void updateProfilePic(Integer id, byte[] img){
        userService.profilePic(id, img);
        updateContactList(id);
    }

    private void updateContactList(Integer id) {
        List<ContactDto> contactList = contactService.getContacts(id);
        List<Integer> contactsId =  contactList.stream()
                .map(ContactDto::getContactID)
                .toList();

        clients.forEach((k,v)->{
            if(contactsId.contains(k)){
                try {
                    v.setContactList(contactService.getContacts(k));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void sendAnnouncement(String announcement) throws RemoteException {

    }

    public void addContact(Integer clientId, String contactPhoneNumber) throws RemoteException {
        CallBackServicesClient client = clients.get(clientId);
        User user;
        try {
            user = new UserRepository().findByPhoneNumber(contactPhoneNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        boolean exists = new ContactService().addContact(clientId, contactPhoneNumber);
        Platform.runLater(() -> {
            try {
                client.contactExists(exists);
                if (clients.containsKey(user.getUserID())) {
                    CallBackServicesClient addedContact = clients.get(user.getUserID());
                    addedContact.setNotificationList(getNotificationList(user.getUserID()));
                    addedContact.notifyClient("You have a new friend request");
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });


    }

    @Override
    public void acceptInvitation(Integer clientId, Integer acceptedUserID) throws RemoteException {
        CallBackServicesClient client = clients.get(clientId);
        CallBackServicesClient acceptedClient = clients.get(acceptedUserID);
        new ContactService().acceptInvitation(clientId, acceptedUserID);

        System.out.println("accepted client" + acceptedUserID);

        Platform.runLater(() -> {
            try {
                client.deleteNotification(acceptedUserID);
                client.setContactList(new ContactService().getContacts(clientId));
                acceptedClient.setContactList(new ContactService().getContacts(acceptedUserID));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void rejectInvitation(Integer clientId, Integer username) throws RemoteException {
        CallBackServicesClient client = clients.get(username);
        new InvitationService().deleteInvitation(clientId, username);
        Platform.runLater(() -> {
            try {
                client.deleteNotification(clientId);
                System.out.println("rejected client" + clientId);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void changeStatus(Integer ID, String status) throws RemoteException {
        try {
            new UserRepository().updateStatus(clients.get(ID).getPhone(), StatusEnum.fromValue(status));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Integer> contacts = ContactsRepository.getInstance().getContacts(ID);
        for (Integer contact : contacts) {
            if (!clients.containsKey(contact))
                continue;
            CallBackServicesClient client = clients.get(contact);
            Platform.runLater(() -> {
                try {
                    client.changeStatus(ID, status);
                    client.setContactList(new ContactService().getContacts(contact));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    @Override
    public ContactDto searchForContact(String phoneNumber) throws RemoteException {
        try {
            User user = new UserRepository().findByPhoneNumber(phoneNumber);
            if (user == null)
                return null;
            else return new ContactService().mapUserToContactDto(user,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createGroupChat(Integer clientId, String text, ArrayList<Integer> selected , byte[] grpImage) throws RemoteException {
        ContactService contactService = new ContactService();
        System.out.println("callback imp : " + clientId);
        ChatDto newGrp =  contactService.createNewGroup(clientId, selected, text, grpImage);
        clients.get(clientId).updateGroupList(newGrp);
    }

    @Override
    public void getPrivateChatMessages(Integer chatId, CallBackServicesClient client) throws RemoteException {
        ArrayList<MessageDTO> messages = new ArrayList<>(MessageServiceImpl.getInstance().getPrivateChatMessages(chatId));
        client.setPrivateMessages(messages,chatId);
    }

    @Override
    public void sendGroupMessage(MessageDTO messageDTO, List<ParticipantDto> participants) throws RemoteException {
        messageService.sendMessage(messageDTO);
        for(ParticipantDto partcipant : participants){
            if(clients.containsKey(partcipant.getParticipantID())){
                CallBackServicesClient client = clients.get(partcipant.getParticipantID());
                Platform.runLater(() -> {
                    try {
                        client.receiveMessage(messageDTO);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    public void notify(Integer clientID,String message){
        if(clients.containsKey(clientID)){
            CallBackServicesClient client = clients.get(clientID);
            Platform.runLater(() -> {
                try {
                    client.notifyClient(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
