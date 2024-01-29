package Interfaces.RmiServices;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicesFactoryInterface extends Remote {
    RemoteUserService createUserService() throws RemoteException;

}
