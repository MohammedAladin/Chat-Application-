// RemoteServiceHandler.java
package org.Client.Controllers;

import Interfaces.RemoteLoginService;
import Interfaces.RemoteRegistrationService;
import javafx.scene.control.Alert;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteServiceHandler {

    private Registry registry;
    private static RemoteServiceHandler remoteServiceHandler;

    private RemoteServiceHandler(){}

    public static RemoteServiceHandler getInstance(){
        if(remoteServiceHandler == null){
            remoteServiceHandler = new RemoteServiceHandler();
        }
        return remoteServiceHandler;
    }

    public RemoteLoginService getRemoteLoginService() {
        RemoteLoginService remoteLoginService;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            remoteLoginService = (RemoteLoginService) registry.lookup("LoginService");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        return remoteLoginService;
    }

    public RemoteRegistrationService getRegistrationService() {
        RemoteRegistrationService registrationService;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            registrationService = (RemoteRegistrationService) registry.lookup("RegistrationService");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        return registrationService;
    }

    public void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User Services");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
