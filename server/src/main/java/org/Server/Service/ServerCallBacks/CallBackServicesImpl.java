package org.Server.Service.ServerCallBacks;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.MessageDTO;
import org.Server.Service.Chat.ChatServices;
import org.Server.Service.Messages.MessageServiceImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallBackServicesImpl extends UnicastRemoteObject implements CallBackServicesServer {
    MessageServiceImpl messageService;
    ChatServices chatServices;
    Map<Integer,CallBackServicesClient> clients = new HashMap<>();



    public void register(CallBackServicesClient client , Integer clientId){
        clients.put(clientId,client);
    }
    public void unRegister(Integer clientId){
        clients.remove(clientId);
    }
    public CallBackServicesImpl() throws RemoteException {
        messageService = MessageServiceImpl.getInstance();
        chatServices = ChatServices.getInstance();
    }
    @Override
    public void sendMessage(MessageDTO messageDTO) throws RemoteException {
        messageService.sendMessage(messageDTO);

        List<Integer> chatParticipantsIds = chatServices.getAllParticipants(messageDTO.getChatID());

    }
    @Override
    public void unRegister(CallBackServicesClient client) throws RemoteException {

    }

    public void sendAnnouncement(String announcement) throws RemoteException{

    }


}
