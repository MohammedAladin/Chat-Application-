package org.Server.Service.User;

import Exceptions.CustomException;
import Interfaces.RmiServices.RemoteUserService;
import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import org.Server.RepoInterfaces.UserRepoInterface;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class UserService extends UnicastRemoteObject implements RemoteUserService {

    RegistrationService registrationService;
    LoginService loginService;
    UserRepoInterface userRepository;
    private static UserService userService;
    private UserService() throws RemoteException {
        super();
        userRepository = new UserRepository();
        registrationService = new RegistrationService((UserRepository)userRepository);
        loginService = new LoginService((UserRepository) userRepository);
    }
    public static UserService getInstance(){
        if(userService == null){
            try {
                userService = new UserService();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return userService;
    }



    @Override
    public boolean registerUser(UserRegistrationDTO user) throws RemoteException {
        try {
            return registrationService.registerUser(user);
        } catch (CustomException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean signInUser(UserLoginDTO userLoginDTO) throws RemoteException {
        return loginService.loginUser(userLoginDTO);
    }
    public User existsByPhoneNumber(String phone){
        try {
            return userRepository.findByPhoneNumber(phone);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
