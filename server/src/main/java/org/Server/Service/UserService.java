package org.Server.Service;

import Interfaces.UserServices;
import Model.Entities.User;
import Model.Enums.StatusEnum;
import Model.Enums.UserField;
import org.Server.Repository.UserRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class UserService extends UnicastRemoteObject implements UserServices {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) throws SQLException, RemoteException {
        try {
            userRepository.save(user);
            System.out.println("User registered successfully: " + user.getPhoneNumber());
        } catch (SQLException e) {
            handleSQLException("Error registering user", e);
        }
    }

    @Override
    public boolean signInUser(String phoneNumber, String password) throws SQLException, RemoteException {
        try {
            User signedUser = userRepository.findById(phoneNumber);

            if (signedUser != null && signedUser.getPassword().equals(password)) {
                userRepository.updateStatus(phoneNumber, StatusEnum.ONLINE);
                System.out.println("User signed in successfully: " + phoneNumber);
                return true;
            }
        } catch (SQLException e) {
            handleSQLException("Error signing in user", e);
        }
        return false;
    }

    @Override
    public boolean existsById(String phoneNumber) throws SQLException, RemoteException {
        try {
            return userRepository.findById(phoneNumber) != null;
        } catch (SQLException e) {
            handleSQLException("Error checking user existence", e);
            return false;
        }
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
