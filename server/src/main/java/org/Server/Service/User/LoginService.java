package org.Server.Service.User;

import Interfaces.RemoteLoginService;
import Model.DTO.UserLoginDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginService extends UnicastRemoteObject implements RemoteLoginService {

    private final UserService userService;

    public LoginService(UserService userService) throws RemoteException {
        super();
        this.userService = userService;
    }
    @Override
    public int loginUser(UserLoginDTO userLoginDTO) throws RemoteException {
        try {
            if (userService.signInUser(userLoginDTO))
                return 0;
            else
                return 1;
        }
        catch (Exception e) {
            return 2;
        }
    }


}
