package Interfaces;

import java.rmi.RemoteException;

public interface UserServiceFactoryI {
    RemoteRegistrationService getRegistrationService() throws RemoteException;
    RemoteLoginService getLoginService() throws RemoteException;
}
