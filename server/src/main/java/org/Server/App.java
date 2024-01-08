package org.Server;

import Interfaces.UserServices;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.Repository.UserRepository;
import org.Server.Service.UserService;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {
    public static void main(String[] args) {
        try {
            DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
            UserRepository userRepository = new UserRepository(connectionManager.getMyConnection());
            UserServices userServices = new UserService(userRepository);

            bindService("UserServices", userServices);
            // bindService("MessagingService", messageServices);
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void bindService(String serviceName, Remote service) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1100);
        registry.rebind(serviceName, service);
    }
}