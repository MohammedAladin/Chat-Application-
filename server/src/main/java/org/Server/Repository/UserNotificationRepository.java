package org.Server.Repository;

import Interfaces.Repository;
import Model.Entities.User;
import Model.Entities.UserNotification;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserNotificationRepository implements Repository<UserNotification,Integer> {
    private final Connection myConnection;

    public UserNotificationRepository(Connection myConnection) {
        this.myConnection = myConnection;
    }
    @Override
    public void save(UserNotification entity) throws SQLException {
        String query = "INSERT INTO UserNotifications (ReceiverID, SenderID, NotificationMessage, NotificationSentDate) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getReceiverID());
            preparedStatement.setInt(2, entity.getSenderID());
            preparedStatement.setString(3, entity.getNotificationMessage());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public UserNotification findById(Integer integer) throws SQLException {
        return null;
    }
    @Override
    public List<UserNotification> findAll() throws SQLException {
        return null;
    }
    @Override
    public void deleteById(Integer notificationId) throws SQLException {
        String query = "DELETE FROM UserNotifications WHERE NotificationID = ?";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, notificationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserNotification> getInvitationsForUser(int userID) throws SQLException {

        List<UserNotification> userNotifications = new ArrayList<>();
        String query = "SELECT * FROM UserNotifications WHERE ReceiverID = ?";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID); // Set the user ID parameter
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userNotifications.add(getNotificationFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  userNotifications;
    }
    public UserNotification getNotificationFromResultSet(ResultSet resultSet) throws SQLException {
        UserNotification userNotification = new UserNotification();
        userNotification.setNotificationID(resultSet.getInt("NotificationID"));
        userNotification.setReceiverID(resultSet.getInt("ReceiverID"));
        userNotification.setSenderID(resultSet.getInt("SenderID"));
        userNotification.setNotificationMessage(resultSet.getString("NotificationMessage"));
        userNotification.setNotificationSentDate(Timestamp.valueOf(resultSet.getTimestamp("NotificationSentDate").toLocalDateTime()));

        return userNotification;
    }
}
