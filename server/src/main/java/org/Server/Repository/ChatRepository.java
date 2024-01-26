package org.Server.Repository;

import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatRepository implements Repository<Chat,Integer> {
    private final Connection connection;

    public ChatRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Chat entity)  {
        String query = "INSERT INTO Chat (ChatName,ChatImage,AdminID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getChatName());
            preparedStatement.setBytes(2, entity.getChatImage());
            preparedStatement.setInt(3, entity.getAdminID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Chat findById(Integer integer) throws SQLException {
        String query ="select * from Chat where ChatID = ?";
        Chat chat= new Chat();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,integer);
            try(ResultSet resultSet =preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    chat.setChatID(integer);
                    chat.setChatName(resultSet.getString("ChatName"));
                    chat.setChatImage(resultSet.getBytes("ChatImage"));
                    chat.setAdminID(resultSet.getInt("AdminID"));
                    chat.setCreationDate(resultSet.getTimestamp("CreationDate"));
                    chat.setLastModified(resultSet.getTimestamp("LastModified"));
                }
            }
        }
        return chat;

    }

    @Override
    public List<Chat> findAll() {
        String query ="select * from Chat";
        List<Chat> chats= new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Chat chat = new Chat();
                    chat.setChatID(resultSet.getInt("ChatID"));
                    chat.setChatName(resultSet.getString("ChatName"));
                    chat.setChatImage(resultSet.getBytes("ChatImage"));
                    chat.setAdminID(resultSet.getInt("AdminID"));
                    chat.setCreationDate(resultSet.getTimestamp("CreationDate"));
                    chat.setLastModified(resultSet.getTimestamp("LastModified"));
                    chats.add(chat);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return chats;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM Chat WHERE ChatID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
