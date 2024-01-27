package org.Server.Repository;

import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ContactsRepository implements Repository<Contact, Integer> {

    private final Connection myConnection;
    private static  ContactsRepository contactsRepository;

    private ContactsRepository() {
        this.myConnection = DatabaseConnectionManager.getInstance().getMyConnection();
    }

    public static ContactsRepository getInstance(){
        if(contactsRepository == null){
            contactsRepository = new ContactsRepository();
        }
        return contactsRepository;
    }
    @Override
    public void save(Contact contact) throws SQLException {
        String query = "INSERT INTO UserContacts (UserID, FriendID, CreationDate) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, contact.getUserID());
            preparedStatement.setInt(2, contact.getFriendID());
            preparedStatement.setTimestamp(3, contact.getCreationDate());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Contact findById(Integer integer) throws SQLException {
        return null;
    }
    @Override
    public List<Contact> findAll() throws SQLException {
        return null;
    }
    @Override
    public void deleteById(Integer integer) throws SQLException {

    }
}
