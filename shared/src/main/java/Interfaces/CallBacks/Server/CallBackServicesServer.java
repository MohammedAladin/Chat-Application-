package Interfaces.CallBacks.Server;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallBackServicesServer extends Remote {

    void sendMessage(MessageDTO messageDTO) throws RemoteException;
    void register(CallBackServicesClient client) throws RemoteException;
    void unRegister(CallBackServicesClient client)throws RemoteException;
    void sendAnnouncement(String announcement) throws RemoteException;
}
