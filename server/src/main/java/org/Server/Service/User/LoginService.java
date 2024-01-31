package org.Server.Service.User;

import Interfaces.RmiServices.RemoteLoginService;
import org.Server.RepoInterfaces.UserRepoInterface;
import Model.DTO.UserLoginDTO;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.ServerModels.Enums.StatusEnum;
import org.Server.Repository.UserRepository;
import org.Server.Service.UserSession;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LoginService implements RemoteLoginService {
    UserRepoInterface userRepository;


    public LoginService(UserRepository userRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;

    }

    public boolean loginUser(UserLoginDTO userLoginDTO) throws RemoteException {
        try {
            User signedUser = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber());

            if (signedUser != null && signedUser.getPassword().equals(userLoginDTO.getPassword())) {
                System.out.println("user phone" + userLoginDTO.getPhoneNumber());
                userRepository.updateStatus(userLoginDTO.getPhoneNumber(), StatusEnum.ONLINE);
                userRepository.updateLoginDate(userLoginDTO.getPhoneNumber(), new Timestamp(System.currentTimeMillis()));

                //callback --> (list of contacts)->ObservUser(Name, id, byte [] image, status, mode, phone)
                //UserSession.setCurrentUser(signedUser);
                System.out.println("User signed in successfully: " + userLoginDTO.getPhoneNumber());
                return true;

            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }
    private void handleSQLException(SQLException e) {
        System.err.println("Error signing in user");
        e.printStackTrace();
    }

}
