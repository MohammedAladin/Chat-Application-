package Interfaces;

import Model.Entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface UserServices extends Remote {
    void registerUser(User user) throws SQLException, RemoteException;

    boolean signInUser(String phone , String password) throws SQLException, RemoteException;
    public boolean existsById(String phoneNumber) throws SQLException, RemoteException;

}
