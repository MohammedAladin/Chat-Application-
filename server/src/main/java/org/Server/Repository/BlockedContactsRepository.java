package org.Server.Repository;

import Model.DTO.BlockedContactDTO;
import org.Server.RepoInterfaces.BlockedContactsRepoInterface;
import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.BlockedEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlockedContactsRepository implements BlockedContactsRepoInterface{

    private final Connection connection;

    public BlockedContactsRepository() {
        this.connection = DatabaseConnectionManager.getInstance().getMyConnection();
    }

    @Override
    public Integer save(BlockedEntity entity) throws SQLException {
        String query = "INSERT INTO BlockedContacts (userID, blockedContactID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getUserID());
            statement.setInt(2, entity.getBlockedContactID());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to insert blocked contact, no ID obtained.");
            }
        }
    }

    @Override
    public BlockedEntity findById(Integer id) throws SQLException {
        String query = "SELECT * FROM BlockedContacts WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new BlockedEntity(
                            resultSet.getInt("userID"),
                            resultSet.getInt("blockedContactID")
                    );
                } else {
                    return null; // Not found
                }
            }
        }
    }

    public List<Integer> findBlockedContactsByUserID(Integer userID) throws SQLException {
        List<Integer> blockedContactIDs = new ArrayList<>();
        String query = "SELECT blockedContactID FROM BlockedContacts WHERE userID=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    blockedContactIDs.add(resultSet.getInt("blockedContactID"));
                }
            }
        }
        return blockedContactIDs;
    }

    @Override
    public List<BlockedEntity> findAll() throws SQLException {
        List<BlockedEntity> blockedContacts = new ArrayList<>();
        String query = "SELECT * FROM BlockedContacts";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                blockedContacts.add(new BlockedEntity(
                        resultSet.getInt("userID"),
                        resultSet.getInt("blockedContactID")
                ));
            }
        }
        return blockedContacts;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM BlockedContacts WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Integer existsByBlockedEntity (BlockedEntity blockedEntity) {
        String query = "SELECT * FROM BlockedContacts WHERE userID = ? AND blockedContactID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Integer result =getID(preparedStatement, blockedEntity.getUserID(), blockedEntity.getBlockedContactID()) ;
            if (result != -1){
                return result;
            }
            result = getID(preparedStatement, blockedEntity.getBlockedContactID(), blockedEntity.getUserID()) ;
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer getIdIfBlocked (BlockedEntity blockedEntity) {
        String query = "SELECT * FROM BlockedContacts WHERE userID = ? AND blockedContactID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return getID(preparedStatement, blockedEntity.getUserID(), blockedEntity.getBlockedContactID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getID (PreparedStatement preparedStatement, Integer userID, Integer blockedContactID) {
        ResultSet resultSet = null;
        try {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, blockedContactID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return resultSet.getInt("ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }
}
