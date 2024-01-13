package org.Server.Service.User;

import Interfaces.RemoteRegistrationService;
import Model.DTO.UserRegistrationDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;

public class RegistrationService extends UnicastRemoteObject implements RemoteRegistrationService {
    private final UserService userService;

    public RegistrationService(UserService userService) throws RemoteException {
        super();
        this.userService = userService;
    }

    @Override
    public int registerUser(String phoneNumber, String email, String displayName, String password, String confirmPassword,
                            Date dateOfBirth, String gender, String country)
            throws RemoteException, SQLException {

        UserRegistrationDTO userDTO = new UserRegistrationDTO(phoneNumber, email, displayName, password, gender, country, dateOfBirth);

        if (userService.existsById(userDTO.getPhoneNumber()) != null) {
            return 0;
        } else {
            if (userService.registerUser(userDTO)) {
                return 1;
            } else return 2;
        }
    }
}
