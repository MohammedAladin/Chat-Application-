package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceFactoryI extends Remote {
    UserServiceFactoryI createUserService() throws RemoteException;

}
