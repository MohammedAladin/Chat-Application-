package Interfaces;
import Model.DTO.UserRegistrationDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;

public interface RemoteRegistrationService extends Remote {
    int registerUser(UserRegistrationDTO userDTO) throws RemoteException, SQLException;
}