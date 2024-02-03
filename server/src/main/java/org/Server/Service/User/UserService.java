package org.Server.Service.User;

import Exceptions.CustomException;
import Interfaces.RmiServices.RemoteUserService;
import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import Model.Enums.UserField;
import org.Server.RepoInterfaces.UserRepoInterface;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;
import org.w3c.dom.Entity;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public void updateUserInfo(String phone, Map<String, String> updatedFields){
      updatedFields.forEach((fieldName, updated)->{
          try {
              if(fieldName.equals(UserField.DATE_OF_BIRTH.getFieldName())){
                  LocalDate localDate = LocalDate.parse(updated);
                  Date dob = Date.valueOf(localDate);
                  userRepository.updateDateOfBirth(phone,dob);
              }
              else userRepository.update(phone,fieldName, updated);
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
      });
    }
    public User existsByPhoneNumber(String phone){
        try {
            return userRepository.findByPhoneNumber(phone);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
