package org.Server.Service;

import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import Model.Entities.User;
import Model.Enums.StatusEnum;
import org.Server.Repository.UserRepository;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class UserService  {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;
    }


    public void registerUser(UserRegistrationDTO user) throws SQLException, RemoteException {
        try {
            userRepository.save(user);
            System.out.println("User registered successfully: " + user.getPhoneNumber());
        } catch (SQLException e) {
            handleSQLException("Error registering user", e);
        }
    }

    public boolean signInUser(UserLoginDTO userLoginDTO) throws SQLException, RemoteException {
        try {
            UserLoginDTO signedUser = userRepository.findById(userLoginDTO.getPhoneNumber());

            if (signedUser != null && signedUser.getPassword().equals(userLoginDTO.getPassword())) {
                userRepository.updateStatus(userLoginDTO.getPhoneNumber(),StatusEnum.ONLINE);
                System.out.println("User signed in successfully: " + userLoginDTO.getPhoneNumber());
                return true;
            }
        } catch (SQLException e) {
            handleSQLException("Error signing in user", e);
        }
        return false;
    }

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
