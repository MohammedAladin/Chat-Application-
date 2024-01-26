package Interfaces;

import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUserService extends Remote {
    public boolean registerUser(UserRegistrationDTO user) throws RemoteException;
    public boolean signInUser(UserLoginDTO userLoginDTO) throws RemoteException;
}
