package Interfaces.CallBacks.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallBackServicesClient extends Remote {

    void ReceiveMessageFromServer() throws RemoteException;
}
