package org.Server.Repository;

import org.Server.RepoInterfaces.Repository;
import org.Server.ServerModels.ServerEntities.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactsRepository implements Repository<Contact, Integer> {

    private final Connection myConnection;
    private static ContactsRepository contactsRepository;

    private ContactsRepository() {
        this.myConnection = DatabaseConnectionManager.getInstance().getMyConnection();
    }

    public static ContactsRepository getInstance() {
        if (contactsRepository == null) {
            contactsRepository = new ContactsRepository();
        }
        return contactsRepository;
    }

    @Override
    public Integer save(Contact contact) throws SQLException {
        String query = "INSERT INTO UserContacts (UserID, FriendID, CreationDate) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, contact.getUserID());
            preparedStatement.setInt(2, contact.getFriendID());
            preparedStatement.setTimestamp(3, contact.getCreationDate());
            preparedStatement.executeUpdate();
        }
        return 0;
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

    public List<Integer> getContacts(int userID)  {
        List<Integer> contacts = new ArrayList<>();
        String query = "SELECT * FROM UserContacts WHERE userid = ? or friendid=?";
        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);
            ResultSet rs = preparedStatement.executeQuery();
            //gets the contacts of the user with the given userID even if they were recorded in the friend id
            while (rs.next()) {
                if (rs.getInt("userid") == userID) {
                    contacts.add(rs.getInt("friendid"));
                } else {
                    contacts.add(rs.getInt("userid"));
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

}
