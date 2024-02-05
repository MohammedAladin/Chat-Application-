package Interfaces.CallBacks.Client;

import Model.DTO.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface CallBackServicesClient extends Remote {

    void ReceiveMessageFromServer() throws RemoteException;
    void receiveAttachment(AttachmentDto attachment) throws RemoteException;

    void setClientId(Integer clientId) throws RemoteException;

    void contactExists(boolean exists) throws RemoteException;

    void setNotificationList(ArrayList<NotificationDTO> notificationDTOS) throws RemoteException;

    void deleteNotification(Integer acceptedUserID) throws RemoteException;

    void setContactList(List<ContactDto> contacts) throws RemoteException;

    void changeStatus(Integer id, String status) throws RemoteException;

     void setData(UserRegistrationDTO userInfo) throws RemoteException;

    String getPhone() throws RemoteException;

    void setGroupList(ArrayList<ChatDto> groupChats) throws RemoteException;
    void updateGroupList(ChatDto newGroup) throws RemoteException;

    void setPrivateMessages(ArrayList<MessageDTO> messages,Integer chatID) throws RemoteException;

    void receiveMessage(MessageDTO messageDTO) throws RemoteException;

    void notifyClient(String message) throws RemoteException;
}
