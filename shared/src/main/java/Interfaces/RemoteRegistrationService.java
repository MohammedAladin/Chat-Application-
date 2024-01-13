package Interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;

public interface RemoteRegistrationService extends Remote {
    int registerUser(String phoneNumber, String email , String displayName, String password, String confirmPassword,
                     Date dateOfBirth, String gender, String country) throws RemoteException, SQLException;
}