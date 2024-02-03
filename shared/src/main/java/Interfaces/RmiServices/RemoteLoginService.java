package Interfaces.RmiServices;

import Model.DTO.UserLoginDTO;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface RemoteLoginService extends Serializable {
    boolean loginUser(UserLoginDTO userLoginDTO) throws RemoteException;

    boolean phoneNumberExists(UserLoginDTO userLoginDTO) throws RemoteException;
}
