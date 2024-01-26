package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerCallbacks extends Remote {
    void register(ClientInterface client) throws RemoteException;
    void unRegister(ClientInterface client)throws RemoteException;
    void sendAnnouncement(String announcement)throws RemoteException;

}
