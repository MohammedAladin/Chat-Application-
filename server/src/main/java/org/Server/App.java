package org.Server;

import Interfaces.RemoteLoginService;
import Interfaces.RemoteRegistrationService;
import Interfaces.ServiceFactoryI;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.Repository.UserRepository;
import org.Server.Service.Factories.ServiceFactory;
import org.Server.Service.User.UserService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {
    static Registry registry;
    public static void main(String[] args) {
        try {
            DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
            UserService userService = new UserService(new UserRepository(connectionManager.getMyConnection()));

            ServiceFactoryI serviceFactory = new ServiceFactory(userService);

            RemoteLoginService loginService = serviceFactory.createUserService().getLoginService();
            RemoteRegistrationService registrationService = serviceFactory.createUserService().getRegistrationService();

            registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LoginService", loginService);
            registry.rebind("RegistrationService", registrationService);

//            for(String s : registry.list()){
//                registry.unbind(s);
//            }

            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}