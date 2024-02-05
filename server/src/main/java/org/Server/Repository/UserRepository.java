package org.Server.Repository;

import org.Server.GUI.Controllers.ServerStatisticsController;
import org.Server.RepoInterfaces.UserRepoInterface;
import org.Server.ServerModels.ServerEntities.User;
import SharedEnums.StatusEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository implements UserRepoInterface {
    private final Connection myConnection;

    public UserRepository() {
        myConnection = DatabaseConnectionManager.getInstance().getMyConnection();
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


    public User findByPhoneNumber(String phoneNumber) throws SQLException {
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
    public User findById(Integer id) throws SQLException {
        String query = "SELECT * FROM UserAccounts WHERE userID = ?";
        User user = null;

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

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
    public void deleteById(Integer id) throws SQLException {

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
        String query = "UPDATE UserAccounts SET UserStatus=? WHERE PhoneNumber=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setString(2, phoneNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateLoginDate(String phone, Timestamp lastLogin) throws SQLException {
        String query = "UPDATE UserAccounts SET LastLogin=? WHERE PhoneNumber=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, lastLogin);
            preparedStatement.setString(2, phone);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDateOfBirth(Integer id, Date dof) throws SQLException {
        String query = "UPDATE UserAccounts SET DateOfBirth=? WHERE UserID=?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setDate(1, new Date(dof.getTime()));
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, String fieldName, String value) throws SQLException {
        String query = "UPDATE UserAccounts SET "+fieldName+" = ? WHERE UserID= ?";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);


            preparedStatement.executeUpdate();
            System.out.println("Updated... ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user =  new User(
                resultSet.getString("PhoneNumber"),
                resultSet.getString("DisplayName"),
                resultSet.getString("EmailAddress"),
                resultSet.getBytes("ProfilePicture"),  // You may need to handle profile picture separately
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

    public void updateUserImage(int i,byte[] image) {
        String query = "UPDATE UserAccounts SET ProfilePicture = ?  WHERE UserID=?";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setBytes(1, image);
            preparedStatement.setInt(2, i);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getAllUsersStatus(){
        Map<String, Integer> userStatusCountMap = new HashMap<>();
        String query = "SELECT userstatus, COUNT(*) AS status_count FROM useraccounts GROUP BY userstatus";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String userStatus = resultSet.getString("userstatus");
                    int count = resultSet.getInt("status_count");
                    userStatusCountMap.put(userStatus, count);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userStatusCountMap;
    }

    public Map<String, Integer> getAllUsersGenders(){
        Map<String, Integer> userGenderCountMap = new HashMap<>();
        String query = "SELECT Gender, COUNT(*) AS gender_count FROM useraccounts GROUP BY Gender";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String userGender = resultSet.getString("gender");
                    int count = resultSet.getInt("gender_count");
                    userGenderCountMap.put(userGender, count);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userGenderCountMap;
    }

    public Map<String, Integer> getAllUsersCountryCount(){
        Map<String, Integer> countryCountMap = new HashMap<>();
        String query = "SELECT Country, COUNT(*) AS country_count FROM useraccounts GROUP BY Country";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String country = resultSet.getString("country");
                    int count = resultSet.getInt("country_count");
                    countryCountMap.put(country, count);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countryCountMap;
    }
}
