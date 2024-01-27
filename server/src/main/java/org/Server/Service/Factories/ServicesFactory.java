package org.Server.Service.Factories;

import Interfaces.ServicesFactoryInterface;
import org.Server.Service.User.UserService;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ServicesFactory extends UnicastRemoteObject implements ServicesFactoryInterface {
    public ServicesFactory() throws RemoteException {
        super();
    }
    public UserService createUserService() throws RemoteException {
        return UserService.getInstance();
    }
}
