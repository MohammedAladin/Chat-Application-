package org.Client.Contollers;

import Interfaces.UserServices;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UserController {

    private static UserController userController;
    private UserController(){}

    public static UserController getInstance(){
        if(userController == null){
            userController = new UserController();
        }
        return userController;
    }

    public UserServices remoteServices(){
        UserServices userService;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            userService = (UserServices) registry.lookup("UserServices");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        return userService;
    }
}
