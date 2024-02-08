package org.Server.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.ChatParticipants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatParticipantRepository implements Repository<ChatParticipants,Integer> {
    private final Connection connection;

    public ChatParticipantRepository() {
        this.connection = DatabaseConnectionManager.getInstance().getMyConnection();
    }
    @Override
    public Integer save(ChatParticipants participant) throws SQLException {
        String query = "INSERT INTO ChatParticipants (ChatID,ParticipantUserID) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, participant.getChatID());
            preparedStatement.setInt(2, participant.getParticipantUserID());
            preparedStatement.executeUpdate();
        }
        return 0;

    }

    @Override
    public ChatParticipants findById(Integer id) throws SQLException {
        String query ="select * from ChatParticipants where ChatID = ?";
        ChatParticipants participant= new ChatParticipants();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                participant.setChatID(id);
                participant.setParticipantUserID(resultSet.getInt("ParticipantUserID"));

            }
        }
        return participant;
    }




    @Override
    public List<ChatParticipants> findAll() throws SQLException {
        String query ="select * from ChatParticipants";
        List<ChatParticipants> participants= new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ChatParticipants participant = new ChatParticipants();
                    participant.setChatID(resultSet.getInt("ChatID"));
                    participant.setParticipantUserID(resultSet.getInt("ParticipantUserID"));
                    participants.add(participant);
                }
            }
        }
        return participants;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM ChatParticipants WHERE ChatID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
    public int getChatIDForParticipants(int participantUserID1, int participantUserID2) {
        String query = "SELECT cp1.ChatID " +
                "FROM ChatParticipants cp1 " +
                "JOIN ChatParticipants cp2 ON cp1.ChatID = cp2.ChatID " +
                "JOIN Chat c ON cp1.ChatID = c.ChatID " +
                "WHERE cp1.ParticipantUserID = ? " +
                "AND cp2.ParticipantUserID = ? " +
                "AND c.AdminID IS NULL";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, participantUserID1);
            statement.setInt(2, participantUserID2);

            System.out.println("getChatIDForParticipants....");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ChatID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
