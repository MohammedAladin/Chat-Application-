package Interfaces.CallBacks.Server;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CallBackServicesServer extends Remote {

    void sendMessage(MessageDTO messageDTO) throws RemoteException;

    void register(CallBackServicesClient client, String clientphone) throws RemoteException;

    void unRegister(Integer client,String phoneNumber) throws RemoteException;

    void sendAnnouncement(String announcement) throws RemoteException;

    void addContact(Integer clientId, String contactPhoneNumber) throws RemoteException;

    void acceptInvitation(Integer clientId, Integer acceptedUserID) throws RemoteException;

    void rejectInvitation(Integer clientId, Integer username) throws RemoteException;

    void changeStatus(Integer ID, String status) throws RemoteException;

    ContactDto searchForContact(String phoneNumber) throws RemoteException;
    void createGroupChat(Integer clientId, String text, ArrayList<Integer> selected) throws RemoteException;



    void getPrivateChatMessages(Integer chatId, CallBackServicesClient client) throws RemoteException;
}
