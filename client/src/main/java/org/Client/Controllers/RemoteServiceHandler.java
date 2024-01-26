// RemoteServiceHandler.java
package org.Client.Controllers;

import Interfaces.RemoteLoginService;
import Interfaces.RemoteRegistrationService;
import Interfaces.RemoteUserService;
import javafx.scene.control.Alert;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteServiceHandler {

    private Registry registry;
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


    public void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User Services");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
