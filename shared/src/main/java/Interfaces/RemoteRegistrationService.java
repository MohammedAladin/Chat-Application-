package Interfaces;

import Exceptions.CustomException;
import Model.DTO.UserRegistrationDTO;

import java.io.Serializable;
import java.sql.SQLException;

public interface RemoteRegistrationService extends Serializable {

    boolean registerUser(UserRegistrationDTO user) throws CustomException, SQLException;
}
