// RemoteServiceHandler.java
package org.Client.Controllers;

import Interfaces.CallBacks.Server.CallBackServicesServer;
import Interfaces.RmiServices.BlockedContactsInterface;
import Interfaces.RmiServices.RemoteUserService;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteServiceHandler {

    private final Registry registry;
    private static RemoteServiceHandler remoteServiceHandler;

    private RemoteServiceHandler(){
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public static RemoteServiceHandler getInstance(){
        if(remoteServiceHandler == null){
            remoteServiceHandler = new RemoteServiceHandler();
        }

        return remoteServiceHandler;
    }
    public RemoteUserService getRemoteUserService() {
        RemoteUserService remoteUserService;
        try {
            remoteUserService = (RemoteUserService) registry.lookup("UserServices");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        return remoteUserService;
    }
    public CallBackServicesServer getCallbacks(){
        CallBackServicesServer serverCallbacks;
        try {
            serverCallbacks = (CallBackServicesServer) registry.lookup("Callbacks");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        return serverCallbacks;
    }

    public BlockedContactsInterface getBlockedContactsService () {
        BlockedContactsInterface blockedContactsService;
        try {
            blockedContactsService = (BlockedContactsInterface) registry.lookup("BlockingServices");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        return blockedContactsService;
    }


    public void showAlert(String content, Alert.AlertType alertType) {
        Platform.runLater(()->{
            Alert alert = new Alert(alertType);
            alert.setTitle("User Services");
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });

    }

}
