package org.Server.Service.Factories;

import Interfaces.UserServiceFactoryI;
import org.Server.Service.User.LoginService;
import org.Server.Service.User.RegistrationService;
import org.Server.Service.User.UserService;
import java.io.Serializable;
import java.rmi.RemoteException;

public class UserServiceFactory implements Serializable, UserServiceFactoryI {

    private final UserService userServiceInstance;
    public UserServiceFactory(UserService userService){
        userServiceInstance = userService;
    }
    public  RegistrationService getRegistrationService() throws RemoteException {
        return new RegistrationService(userServiceInstance);
    }
    public  LoginService getLoginService() throws RemoteException {
        return new LoginService(userServiceInstance);
    }
}
