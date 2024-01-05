package org.example.Repositiers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.Model.User;

public class UserRepository implements Repository<User, String> {
    private Connection myConnection;

    public UserRepository(Connection myConnection) {
        this.myConnection = myConnection;
    }

    

    @Override
    public void save(User user) throws SQLException {
        String query = "INSERT INTO Users (PhoneNumber, DisplayName, Email, Picture, Password, Gender, Country, DateOfBirth, Bio) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getDisplayName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPicture());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setDate(8, new java.sql.Date(user.getDateOfBirth().getTime()));
            preparedStatement.setString(9, user.getBio());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @Override
    public User findById(String phoneNumber) throws SQLException {
        String query = "SELECT * FROM Users WHERE PhoneNumber = ?";
        User user = null;

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {

            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return user;
    }

    @Override
    public List<User> findAll() throws SQLException {
        String query = "SELECT * FROM Users";
        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                userList.add(mapResultSetToUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return userList;
    }

    @Override
    public void update(User user) throws SQLException {
       
    }

    @Override
    public void delete(String phoneNumber) throws SQLException {
        
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserID(resultSet.getInt("UserID"));
        user.setPhoneNumber(resultSet.getString("PhoneNumber"));
        user.setDisplayName(resultSet.getString("DisplayName"));
        user.setEmail(resultSet.getString("Email"));
        user.setPicture(resultSet.getString("Picture"));
        user.setPassword(resultSet.getString("Password"));
        user.setGender(resultSet.getString("Gender"));
        user.setCountry(resultSet.getString("Country"));
        user.setDateOfBirth(resultSet.getDate("DateOfBirth"));
        user.setBio(resultSet.getString("Bio"));
        user.setStatus(resultSet.getString("Status"));
        user.setSpecificStatus(resultSet.getString("SpecificStatus"));
    
        return user;
    }
    
}

