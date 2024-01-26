package org.Server.Service.User;

import Exceptions.CustomException;
import Interfaces.RemoteRegistrationService;
import Model.DTO.UserRegistrationDTO;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.RepoInterfaces.UserRepoInterface;
import org.Server.Repository.UserRepository;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class RegistrationService implements RemoteRegistrationService {
    UserRepoInterface userRepository;

    public RegistrationService(UserRepository userRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;

    }
    public boolean registerUser(UserRegistrationDTO user) throws CustomException, SQLException {

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) {
            return false;
        }
        else userRepository.save(toUser(user));
        return true;
    }
    public User toUser(UserRegistrationDTO userD) {
        return new User(// Assuming userID is not available at the time of registration or set to 0 as a placeholder
                userD.getPhoneNumber(),
                userD.getDisplayName(),
                userD.getEmailAddress(),
                null,  // You may need to handle profile picture separately
                userD.getPasswordHash(),
                userD.getGender(),
                userD.getCountry(),
                userD.getDateOfBirth(),
                null,  // bio is not available at the time of registration
                "Available",  // Assuming default user status is "Offline"
                "Offline",  // Assuming default user mode is "Offline"
                null  // lastLogin is not available at the time of registration
        );
    }
}

