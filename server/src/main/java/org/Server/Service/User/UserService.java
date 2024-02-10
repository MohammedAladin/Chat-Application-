package org.Server.Service.User;

import Exceptions.CustomException;
import Interfaces.RmiServices.RemoteUserService;
import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import Model.Enums.UserField;
import org.Server.RepoInterfaces.UserRepoInterface;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Date;
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
    @Override
    public boolean existsByUserLoginDTO(UserLoginDTO userLoginDTO) throws RemoteException {
        if (loginService.phoneNumberExists(userLoginDTO)) return true;
        return false;
    }

    public User existsByPhoneNumber(String phone){
        try {
            return userRepository.findByPhoneNumber(phone);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserInfo(Integer id, Map<String, String> updatedFields){
        updatedFields.forEach((key,value)->{
            if(key.equals(UserField.DATE_OF_BIRTH.getFieldName())){
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(value);
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    userRepository.updateDateOfBirth(id,sqlDate);
                } catch (ParseException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                try {
                    System.out.println("Updating Name.. ");
                    userRepository.update(id,key,value);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println("After Updating... ");
    }
    public void profilePic(Integer id, byte[] img){
        try {
            userRepository.updateUserImage(id, img);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public UserRegistrationDTO toUserDto(User userD) {
        return new UserRegistrationDTO(
                userD.getPhoneNumber(),
                userD.getDisplayName(),
                userD.getEmailAddress(),
                userD.getPassword(),
                userD.getGender(),
                userD.getCountry(),
                (java.sql.Date) userD.getDateOfBirth(),
                userD.getProfilePicture(),
                userD.getBio()
        );
    }

}
