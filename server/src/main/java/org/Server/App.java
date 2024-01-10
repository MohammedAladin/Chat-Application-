package org.Server;

import Interfaces.RemoteLoginService;
import Interfaces.RemoteRegistrationService;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.Repository.UserRepository;
import org.Server.Service.*;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {
    static Registry registry;
    public static void main(String[] args) {
        try {
            DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
            UserRepository userRepository = new UserRepository(connectionManager.getMyConnection());
            UserService userService = new UserService(userRepository);

            RemoteRegistrationService registrationService = new RegistrationService(userService);

            RemoteLoginService loginService = new LoginService(userService);

            registry = LocateRegistry.createRegistry(1099);

            bindService("RegistrationServices", registrationService);
            bindService("LoginServices", loginService);

            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void bindService(String serviceName, Remote service) throws Exception {
        registry.rebind(serviceName, service);
    }
}