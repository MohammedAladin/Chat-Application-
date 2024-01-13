package org.Server.Repository;

import Interfaces.Repository;
import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import Model.Enums.StatusEnum;
import org.Server.Service.UserSession;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<UserRegistrationDTO, String>{
    private final Connection myConnection;
    public UserRepository(Connection myConnection) {
        this.myConnection = myConnection;
    }

    @Override
    public void save(UserRegistrationDTO user) throws SQLException {
        String query = "INSERT INTO Users (PhoneNumber, DisplayName,Email ,Password, Gender, Country, DateOfBirth, Bio, Status, SpecificStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getDisplayName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setDate(7, user.getDateOfBirth());
            preparedStatement.setString(8, "BIO");
            preparedStatement.setString(9, "Offline");
            preparedStatement.setString(10, "Available");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserRegistrationDTO findById(String phoneNumber) throws SQLException {
        String query = "SELECT * FROM Users WHERE PhoneNumber = ?";
        UserRegistrationDTO user = null;

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {

            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUserDTO(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<UserRegistrationDTO> findAll() throws SQLException {
        String query = "SELECT * FROM Users";
        List<UserRegistrationDTO> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                userList.add(mapResultSetToUserDTO(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void update(String phoneNumber, String fieldName, String value) throws SQLException {
        String query = "UPDATE Users SET " + fieldName + "=? WHERE PhoneNumber=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {

            preparedStatement.setString(1, value);
            preparedStatement.setString(2, phoneNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(String phoneNumber, StatusEnum status) throws SQLException {
        String query = "UPDATE Users SET Status=? WHERE PhoneNumber=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setString(2, phoneNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @Override
    public void delete(String phoneNumber) throws SQLException {
        // Implement deletion logic if needed
    }

    private UserRegistrationDTO mapResultSetToUserDTO(ResultSet resultSet) throws SQLException {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                resultSet.getString("PhoneNumber"),
                resultSet.getString("email"),
                resultSet.getString("DisplayName"),
                resultSet.getString("Password"),
                resultSet.getString("Gender"),
                resultSet.getString("Country"),
                resultSet.getDate("DateOfBirth")
        );

        userDTO.setBio(resultSet.getString("Bio"));
        userDTO.setStatus(resultSet.getString("Status"));
        userDTO.setSpecificStatus(resultSet.getString("SpecificStatus"));

        return userDTO;
    }
}
