package Interfaces.CallBacks.Client;

import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import Model.DTO.NotificationDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface CallBackServicesClient extends Remote {

    void ReceiveMessageFromServer() throws RemoteException;

    void setClientId(Integer clientId) throws RemoteException;

    void contactExists(boolean exists) throws RemoteException;

    void setNotificationList(ArrayList<NotificationDTO> notificationDTOS) throws RemoteException;

    void deleteNotification(Integer acceptedUserID) throws RemoteException;

    void setContactList(List<ContactDto> contacts) throws RemoteException;

    void changeStatus(Integer id, String status) throws RemoteException;


    void setData(String clientphone,String name,byte[] pic) throws RemoteException;

    String getPhone() throws RemoteException;

    void setGroupList(ArrayList<ChatDto> groupChats) throws RemoteException;
}
