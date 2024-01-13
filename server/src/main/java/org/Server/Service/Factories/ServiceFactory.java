package org.Server.Service.Factories;

import Interfaces.ServiceFactoryI;

import org.Server.Repository.UserRepository;
import org.Server.Service.User.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class ServiceFactory extends UnicastRemoteObject implements ServiceFactoryI {

    UserService userService;
    public ServiceFactory(UserService userService) throws RemoteException {
        super();
        this.userService = userService;
    }
    public UserServiceFactory createUserService() throws RemoteException {
        return new UserServiceFactory(userService);
    }

}
