package org.Server.Service.User;

import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import Model.Entities.User;
import Model.Enums.StatusEnum;
import org.Server.Repository.UserRepository;
import org.Server.Service.UserSession;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;
    }
    public boolean registerUser(User user) throws RemoteException {
        try {
            userRepository.save(user);
            System.out.println("User registered successfully: " + user.getPhoneNumber());
            return true;
        } catch (SQLException e) {
            handleSQLException("Error registering user", e);
        }
        return false;
    }

    public boolean signInUser(UserLoginDTO userLoginDTO) throws SQLException, RemoteException {
        try {
            User signedUser = userRepository.findById(userLoginDTO.getPhoneNumber());

            if (signedUser != null && signedUser.getPassword().equals(userLoginDTO.getPassword())) {
                userRepository.updateStatus(userLoginDTO.getPhoneNumber(), StatusEnum.ONLINE);

                UserSession.setCurrentUser(signedUser);
                System.out.println("User signed in successfully: " + userLoginDTO.getPhoneNumber());
                return true;
            }
        } catch (SQLException e) {
            handleSQLException("Error signing in user", e);
        }
        return false;
    }

    public User existsById(String phoneNumber) throws SQLException {
        return userRepository.findById(phoneNumber);
    }

    private void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
