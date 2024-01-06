package Interfaces;

import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface Registration extends Remote {
    void registerUser(User user) throws SQLException, RemoteException;
    public boolean existsById(String phoneNumber) throws SQLException, RemoteException;

}
