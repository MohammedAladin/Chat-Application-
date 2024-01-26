package org.Server.GUI.Controllers;

import Interfaces.ServiceFactoryI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.Repository.UserRepository;
import org.Server.Service.Factories.ServiceFactory;
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
    private RegistrationService registrationService;
    private LoginService loginService;
    private Registry registry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
            UserService userService = new UserService(new UserRepository(connectionManager.getMyConnection()));
            ServiceFactoryI serviceFactory = new ServiceFactory(userService);
            registrationService = (RegistrationService) serviceFactory.createUserService().getRegistrationService();
            loginService = (LoginService) serviceFactory.createUserService().getLoginService();
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    private void start (){
        try {
            registry.rebind("loginService", loginService);
            registry.rebind("registrationService", registrationService);
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
