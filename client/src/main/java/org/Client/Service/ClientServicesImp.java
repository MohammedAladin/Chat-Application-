package org.Client.Service;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;
import Model.DTO.NotificationDTO;
import javafx.application.Platform;
import org.Client.Models.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientServicesImp extends UnicastRemoteObject implements CallBackServicesClient {

    public ClientServicesImp() throws RemoteException {
        super();
    }
    public void sendAnnouncement(String message) throws RemoteException{
        Model.getInstance().getViewFactory().serverAnnouncementProperty().set(message);
    }

    @Override
    public void ReceiveMessageFromServer() throws RemoteException {

    }

    @Override
    public void setClientId(Integer clientId) throws RemoteException {
        Model.getInstance().setClientId(clientId);
    }

    @Override
    public void contactExists(boolean exists) throws RemoteException {
        Model.getInstance().setContactExists(exists);
    }

    @Override
    public void setNotificationList(ArrayList<NotificationDTO> notificationDTOS) throws RemoteException {
        Model.getInstance().setNotifications(notificationDTOS);
    }

    @Override
    public void deleteNotification(Integer sendingUserID) throws RemoteException {
        Platform.runLater(()->{
            Model.getInstance().getNotifications().removeIf(notificationDTO -> notificationDTO.getSenderID().equals(sendingUserID));
            System.out.println(Model.getInstance().getNotifications());
        });
    }

    public void setContactList(List<ContactDto> contacts) throws RemoteException{
        Platform.runLater(()->Model.getInstance().setContacts(contacts));
    }

    @Override
    public void changeStatus(Integer id, String status) throws RemoteException {
        Platform.runLater(()->{
            Model.getInstance().getContacts().stream().filter(contactDto -> contactDto.getContactID().equals(id)).findFirst().get().setStatus(status);
            System.out.println(id + "changed status to " + status);
            for(ContactDto contactDto : Model.getInstance().getContacts()){
                System.out.println(contactDto.getContactID() + " " + contactDto.getStatus());
            }
        });
    }

    @Override
    public void setData(String clientphone,String name,byte[] profilepic) throws RemoteException {
        Platform.runLater(()->{Model.getInstance().setPhoneNumber(clientphone);
        Model.getInstance().setDisplayName(name);
        Model.getInstance().setProfilePicture(profilepic);
    });
    }

    @Override
    public String getPhone() throws RemoteException {
        return Model.getInstance().getPhoneNumber();
    }

    @Override
    public void setGroupList(ArrayList<ChatDto> groupChats) throws RemoteException {
        Platform.runLater(()->Model.getInstance().setGroupList(groupChats));
        System.out.println("Group list set to " + groupChats);
    }

    @Override
    public void updateGroupList(ChatDto newGroup) throws RemoteException {
        Model.getInstance().getGroupList().add(newGroup);
    }

    @Override
    public void setPrivateMessages(ArrayList<MessageDTO> messages,Integer chatId) throws RemoteException {
        Platform.runLater(()->Model.getInstance().setPrivateChats(chatId,messages));
    }

    @Override
    public void receiveMessage(MessageDTO messageDTO) throws RemoteException {
        Platform.runLater(()->Model.getInstance().addMessage(messageDTO));
    }

}
