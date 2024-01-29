package org.Client.Service;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import org.Client.Models.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServicesImp extends UnicastRemoteObject implements CallBackServicesClient {

    public ClientServicesImp() throws RemoteException {
        super();
    }
    public void sendAnnouncement(String message) throws RemoteException{
        Model.getInstance().getViewFactory().serverAnnouncementProperty().set(message);
    }

    @Override
    public void ReceiveMessageFromServer() throws RemoteException {

    }
}
