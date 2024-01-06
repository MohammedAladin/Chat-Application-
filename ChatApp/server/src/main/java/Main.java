import Interfaces.Registration;
import Interfaces.Repository;
import iti.jets.Repository.DatabaseConnectionManager;
import iti.jets.Repository.UserRepository;
import iti.jets.Service.UserService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnectionManager connectionManager = new DatabaseConnectionManager();
            UserRepository userRepository = new UserRepository(connectionManager.getMyConnection());
            Registration registrationService = new UserService(userRepository);


            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RegistrationService", registrationService);
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}