package Interfaces;
import Model.DTO.UserLoginDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteLoginService extends Remote {
    int loginUser(UserLoginDTO userLoginDTO) throws RemoteException;
}
