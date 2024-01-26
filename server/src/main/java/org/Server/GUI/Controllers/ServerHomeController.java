package org.Server.GUI.Controllers;

import Interfaces.RemoteUserService;
import Interfaces.ServicesFactoryInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.Repository.UserRepository;
import org.Server.Service.Factories.ServicesFactory;
import org.Server.Service.User.LoginService;
import org.Server.Service.User.RegistrationService;
import org.Server.Service.User.UserService;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class ServerHomeController implements Initializable {
    private RemoteUserService userService;
    private Registry registry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServicesFactoryInterface serviceFactory = new ServicesFactory();
            userService = serviceFactory.createUserService();
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    private void start (){
        try {
            registry.rebind("UserServices", userService);
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
}
