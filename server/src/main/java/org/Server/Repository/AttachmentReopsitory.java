package org.Server.Repository;

import org.Server.RepoInterfaces.AttachmentRepoInterface;
import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.Attachment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentReopsitory implements AttachmentRepoInterface {
    private final Connection connection;
    private static AttachmentReopsitory attachmentReopsitory;
    private AttachmentReopsitory(){
        this.connection = DatabaseConnectionManager.getInstance().getMyConnection();
    }
    public static AttachmentReopsitory getInstance(){
        if(attachmentReopsitory==null){
            attachmentReopsitory = new AttachmentReopsitory();
        }
        return attachmentReopsitory;
    }

    @Override
    public Integer save(Attachment attachment) {
        String query = "INSERT INTO attachment (MessageID, Attachment) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, attachment.getMessageID());
            preparedStatement.setBytes(2, attachment.getAttachment());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating attachment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Assuming AttachmentID is an INT
                } else {
                    throw new SQLException("Creating attachment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Attachment findById(Integer id) {
        String query = "select * from attachment where AttachmentID = ?";
        Attachment attachment = new Attachment();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
           try (ResultSet resultSet = preparedStatement.executeQuery()) {
               if (resultSet.next()) {
                   attachment.setAttachmentID(id);
                   attachment.setMessageID(resultSet.getInt("MessageID"));
                   attachment.setAttachment(resultSet.getBytes("Attachment"));
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attachment;
    }
    public Attachment findByMessageId(Integer messageId) {
        String query = "SELECT * FROM Attachment WHERE MessageID = ? ;";

        Attachment attachment = new Attachment();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, messageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    attachment.setAttachmentID(messageId);
                    attachment.setMessageID(resultSet.getInt("MessageID"));
                    attachment.setAttachment(resultSet.getBytes("Attachment"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attachment;
    }

    @Override
    public List<Attachment> findAll() {
        String query = "select * from attachment";
        List<Attachment> attachments = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attachment attachment = new Attachment();
                    attachment.setAttachmentID(resultSet.getInt("AttachmentID"));
                    attachment.setMessageID(resultSet.getInt("MessageID"));
                    attachment.setAttachment(resultSet.getBytes("Attachment"));
                    attachments.add(attachment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attachments;
    }

    @Override
    public void deleteById(Integer id) {
        String query = "delete from attachment where AttachmentID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
