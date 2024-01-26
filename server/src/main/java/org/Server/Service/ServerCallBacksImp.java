package org.Server.Service;

import Interfaces.ClientInterface;
import Interfaces.ServerCallbacks;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerCallBacksImp extends UnicastRemoteObject implements ServerCallbacks {

    List<ClientInterface> clients = new ArrayList<>();
    public ServerCallBacksImp() throws RemoteException {
        super();
    }
    public void register(ClientInterface client) throws RemoteException{
        clients.add(client);
    }
    public void unRegister(ClientInterface client) throws RemoteException{
        clients.remove(client);
    }
    public void sendAnnouncement(String announcement) throws RemoteException{
        for(ClientInterface client : clients){
            client.sendAnnouncement(announcement);
        }
    }

    public void updateStatus(){

    }
}
