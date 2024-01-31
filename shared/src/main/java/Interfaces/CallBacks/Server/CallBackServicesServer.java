package Interfaces.CallBacks.Server;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallBackServicesServer extends Remote {

    void sendMessage(MessageDTO messageDTO) throws RemoteException;

    void register(CallBackServicesClient client, String clientphone) throws RemoteException;

    void unRegister(CallBackServicesClient client) throws RemoteException;

    void sendAnnouncement(String announcement) throws RemoteException;

    void addContact(Integer clientId, String contactPhoneNumber) throws RemoteException;

    void acceptInvitation(Integer clientId, Integer acceptedUserID) throws RemoteException;

    void rejectInvitation(Integer clientId, Integer username) throws RemoteException;

    void changeStatus(Integer ID, String status) throws RemoteException;

    ContactDto searchForContact(String phoneNumber) throws RemoteException;
}
