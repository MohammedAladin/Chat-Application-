package org.Server.Repository;

import Interfaces.Repository;
import Model.DTO.UserRegistrationDTO;
import Model.Entities.User;
import Model.Enums.StatusEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User, String> {
    private final Connection myConnection;

    public UserRepository(Connection myConnection) {
        this.myConnection = myConnection;
    }

    @Override
    public void save(User user) throws SQLException {
        String query = "INSERT INTO UserAccounts (PhoneNumber, DisplayName, EmailAddress, PasswordHash, Gender, Country, DateOfBirth, Bio, UserStatus, UserMode, LastLogin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getDisplayName());
            preparedStatement.setString(3, user.getEmailAddress());

            preparedStatement.setString(4, user.getPassword()); // Use getPasswordHash instead of getPassword
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setDate(7, new Date(user.getDateOfBirth().getTime())); // Convert Date to java.sql.Date
            preparedStatement.setString(8, user.getBio());
            preparedStatement.setString(9, user.getUserStatus());
            preparedStatement.setString(10, user.getUserMode());
            preparedStatement.setTimestamp(11, user.getLastLogin());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(String phoneNumber) throws SQLException {
        String query = "SELECT * FROM UserAccounts WHERE PhoneNumber = ?";
        User user = null;

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    @Override
    public void deleteById(String s) throws SQLException {

    }


    @Override
    public List<User> findAll() throws SQLException {
        String query = "SELECT * FROM UserAccounts";
        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
    public void updateStatus(String phoneNumber, StatusEnum status) throws SQLException {
        String query = "UPDATE Users SET Status=? WHERE PhoneNumber=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setString(2, phoneNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(String phoneNumber, String fieldName, String value) throws SQLException {
        String query = "UPDATE UserAccounts SET " + fieldName + "=? WHERE PhoneNumber=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, phoneNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user =  new User(
                resultSet.getString("PhoneNumber"),
                resultSet.getString("DisplayName"),
                resultSet.getString("EmailAddress"),
                null,  // You may need to handle profile picture separately
                resultSet.getString("PasswordHash"),  // Use PasswordHash instead of Password
                resultSet.getString("Gender"),
                resultSet.getString("Country"),
                resultSet.getDate("DateOfBirth"),
                resultSet.getString("Bio"),
                resultSet.getString("UserStatus"),
                resultSet.getString("UserMode"),
                resultSet.getTimestamp("LastLogin")
        );
        user.setUserID(resultSet.getInt("UserID"));

        return user;
    }

}
