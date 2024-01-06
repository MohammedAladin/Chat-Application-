package iti.jets.Service;



import Interfaces.Registration;
import Model.User;
import iti.jets.Repository.UserRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class UserService extends UnicastRemoteObject implements Registration {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) throws SQLException, RemoteException {
        userRepository.save(user);
    }

    @Override
    public boolean existsById(String phoneNumber) throws SQLException, RemoteException {
      if(userRepository.findById(phoneNumber)!=null){
        return true;
      }
      return false;
    }
}