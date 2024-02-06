package Interfaces.CallBacks.Server;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CallBackServicesServer extends Remote {

    void sendMessage(MessageDTO messageDTO) throws RemoteException;
    void registerForChatBot(Integer participantId, Integer chatId)throws RemoteException;
    void unRegisterFromChatBot(Integer participantId, Integer chatId) throws RemoteException;

    void register(CallBackServicesClient client, String clientphone) throws RemoteException;

    void unRegister(Integer client,String phoneNumber) throws RemoteException;

    void sendAnnouncement(String announcement) throws RemoteException;

    void addContact(Integer clientId, String contactPhoneNumber) throws RemoteException;

    void acceptInvitation(Integer clientId, Integer acceptedUserID) throws RemoteException;

    void rejectInvitation(Integer clientId, Integer username) throws RemoteException;

    void changeStatus(Integer ID, String status) throws RemoteException;

    ContactDto searchForContact(String phoneNumber) throws RemoteException;
    void createGroupChat(Integer clientId, String text, ArrayList<Integer> selected , byte[] grpImage) throws RemoteException;

    public void sendAttachment(AttachmentDto attachmentMessage) throws RemoteException;

    void getPrivateChatMessages(Integer chatId, CallBackServicesClient client) throws RemoteException;
    void updateProfile(Integer id, Map<String, String> updatedFields) throws  RemoteException;
     void updateProfilePic(Integer id, byte[] img) throws RemoteException;

    void sendGroupMessage(MessageDTO messageDTO, List<ParticipantDto> participants) throws RemoteException;
    void notify(Integer clientID,String message) throws RemoteException;
   void downloadAttachment(Integer clientid, Integer attachmentID, String message) throws RemoteException;
}
