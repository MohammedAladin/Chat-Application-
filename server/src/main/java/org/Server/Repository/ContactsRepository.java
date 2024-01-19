package org.Server.Repository;

import Interfaces.Repository;
import Model.Entities.Contact;
import Model.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactsRepository implements Repository<Contact, Integer> {

    private final Connection myConnection;

    public ContactsRepository(Connection myConnection) {
        this.myConnection = myConnection;
    }
    @Override
    public void save(Contact contact) throws SQLException {
        String query = "INSERT INTO Contacts (UserID, FriendID) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
            preparedStatement.setInt(1, contact.getUserID());
            preparedStatement.setInt(2, contact.getFriendID());
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
