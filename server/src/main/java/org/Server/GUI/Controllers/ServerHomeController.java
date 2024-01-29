package org.Server.GUI.Controllers;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Client.RemoteClientAnnouncement;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Interfaces.RmiServices.RemoteUserService;
import Interfaces.RmiServices.ServicesFactoryInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.Server.Service.Factories.ServicesFactory;
import org.Server.Service.ServerCallBacks.CallBackServicesImpl;


import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class ServerHomeController implements Initializable {

    public Button announceButton;
    public TextField announcementField;
    private RemoteUserService userService;
    private Registry registry;
    private CallBackServicesServer callBackServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServicesFactoryInterface serviceFactory = new ServicesFactory();
            userService = serviceFactory.createUserService();
            callBackServices = new CallBackServicesImpl();
            registry = LocateRegistry.createRegistry(1099);

            announceButton.setOnAction((e)->handleAnnouncement());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    private void start (){
        try {
            registry.rebind("UserServices", userService);
            registry.rebind("Callbacks", callBackServices);
            System.out.println("working");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void stop (){
        try {
            for (String name : registry.list()){
                registry.unbind(name);
            }
            System.out.println("stopping");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleAnnouncement() {
        String announcement = announcementField.getText();
        try {
            callBackServices.sendAnnouncement(announcement);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
