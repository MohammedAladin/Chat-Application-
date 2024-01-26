package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void sendAnnouncement(String message) throws RemoteException;
}
