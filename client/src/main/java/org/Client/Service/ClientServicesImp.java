package org.Client.Service;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.ContactDto;
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
        Model.getInstance().setContacts(contacts);
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
}
