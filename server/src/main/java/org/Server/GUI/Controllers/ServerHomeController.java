package org.Server.GUI.Controllers;

import Interfaces.CallBacks.Server.CallBackServicesServer;
import Interfaces.RmiServices.BlockedContactsInterface;
import Interfaces.RmiServices.RemoteUserService;
import Interfaces.RmiServices.ServicesFactoryInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.Server.Service.Contacts.BlockedContactsService;
import org.Server.Service.Factories.ServicesFactory;
import org.Server.Service.ServerCallBacks.CallBackServicesImpl;


import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;

public class ServerHomeController implements Initializable {

    public Button announceButton;
    public TextField announcementField;
    private RemoteUserService userService;
    private Registry registry;
    private CallBackServicesServer callBackServices;
    private BlockedContactsInterface blockedContactsService;
    private ScheduledExecutorService executorService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ServicesFactoryInterface serviceFactory = new ServicesFactory();
            userService = serviceFactory.createUserService();

            callBackServices = new CallBackServicesImpl();
            blockedContactsService = new BlockedContactsService();

            registry = LocateRegistry.createRegistry(2000);
            announceButton.setOnAction((e)->handleAnnouncement());

            executorService = CallBackServicesImpl.executorService;


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void start (){
        try {

            Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));

            registry.rebind("UserServices", userService);
            registry.rebind("Callbacks", callBackServices);
            registry.rebind("BlockingServices", blockedContactsService);

            System.out.println("working");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void stop (){
        try {
            for (String name : registry.list()){
                registry.unbind(name);
                System.out.println(name);
            }

            CallBackServicesImpl.clients.forEach((id, client)-> {
                try {
                    client.logout();
                    client.sendAnnouncement("Sorry, The Server Is not Running Right Now, Try To login Later..");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
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
