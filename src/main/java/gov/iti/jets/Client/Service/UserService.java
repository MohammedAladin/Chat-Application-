package gov.iti.jets.Client.Service;


import java.sql.SQLException;

import gov.iti.jets.Client.Model.User;
import gov.iti.jets.Client.Repositiers.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws SQLException {
        userRepository.save(user);
    }

    public boolean existsById(String phoneNumber) throws SQLException {
      if(userRepository.findById(phoneNumber)!=null){
        return true;
      }
      return false;
    }


}