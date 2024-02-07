package org.Server.GUI.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ServerDatabaseController implements Initializable {
    @FXML
    public VBox root;
    UserRepository userRepository = new UserRepository();
    TableView<User> tableView = new TableView<User>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createColumns();
        List<User> users;
        try {
             users = userRepository.findAll();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        users.forEach(user -> tableView.getItems().add(user));

        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tableView.prefHeightProperty().bind(root.heightProperty());

        root.getChildren().add(tableView);
    }

    private void createColumns (){
        // PhoneNumbers
        TableColumn<User, String> phoneNumbersColumn = new TableColumn<User, String>("Phone Number");
        phoneNumbersColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
        phoneNumbersColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumbersColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setPhoneNumber(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(phoneNumbersColumn);

        // User IDs
        TableColumn<User, Integer> userIDsColumn = new TableColumn<User, Integer>("User ID");
        userIDsColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
        tableView.getColumns().add(userIDsColumn);

        // Display Names
        TableColumn<User, String> displayNamesColumn = new TableColumn<User, String>("Display Name");
        displayNamesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("displayName"));
        displayNamesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        displayNamesColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setDisplayName(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(displayNamesColumn);

        // Email Addresses
        TableColumn<User, String> emailAddressesColumn = new TableColumn<User, String>("Email Address");
        emailAddressesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("emailAddress"));
        emailAddressesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailAddressesColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setEmailAddress(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(emailAddressesColumn);

        // Passwords
        TableColumn<User, String> passwordsColumn = new TableColumn<User, String>("Password");
        passwordsColumn.setCellValueFactory(new PropertyValueFactory<User, String>("passwordHash"));
        passwordsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordsColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setPasswordHash(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(passwordsColumn);

        // Genders
        TableColumn<User, String> gendersColumn = new TableColumn<User, String>("Gender");
        gendersColumn.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        gendersColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gendersColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setGender(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(gendersColumn);

        // Countries
        TableColumn<User, String> countriesColumn = new TableColumn<User, String>("Country");
        countriesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
        countriesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        countriesColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setCountry(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(countriesColumn);

        // Date of Birth
        TableColumn<User, Date> datesColumn = new TableColumn<User, Date>("Date of Birth");
        datesColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("dateOfBirth"));
        datesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        datesColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setDateOfBirth(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(datesColumn);

        // Bio
        TableColumn<User, String> biosColumn = new TableColumn<User, String>("Bio");
        biosColumn.setCellValueFactory(new PropertyValueFactory<User, String>("bio"));
        biosColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        biosColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setBio(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(biosColumn);

        // Status
        TableColumn<User, String> statusesColumn = new TableColumn<User, String>("Status");
        statusesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userStatus"));
        statusesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusesColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setUserStatus(event.getNewValue());
            try {
                userRepository.updateUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tableView.getColumns().add(statusesColumn);
    }
}
