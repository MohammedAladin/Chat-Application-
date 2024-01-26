package org.Client.Service;

import Interfaces.ClientInterface;
import org.Client.Models.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceImp extends UnicastRemoteObject implements ClientInterface {

    public ClientServiceImp() throws RemoteException {
        super();
    }
    public void sendAnnouncement(String message) throws RemoteException{
        Model.getInstance().getViewFactory().serverAnnouncementProperty().set(message);
    }

}
