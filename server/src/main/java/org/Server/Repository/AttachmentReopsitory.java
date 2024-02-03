package org.Server.Repository;

import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.Attachment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AttachmentReopsitory implements Repository<Attachment,Integer>{
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
    public void save(Attachment attachment){
        String query = "INSERT INTO attachment (AttachmentID,MessageID,Attachment) VALUES (?, ?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, attachment.getAttachmentID());
            preparedStatement.setInt(2, attachment.getMessageID());
            preparedStatement.setBytes(3, attachment.getAttachment());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
