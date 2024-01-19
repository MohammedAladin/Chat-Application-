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
    public int registerUser(UserRegistrationDTO userDTO)
            throws RemoteException, SQLException {

        if (userService.existsById(userDTO.getPhoneNumber()) != null) {
            return 0;
        }
        if (userService.registerUser(userDTO.toUser())) {
            return 1;
        }
            throw new SQLException("Can't Save New User");
    }
}

