package org.example.Views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.Model.User;
import org.example.Repositiers.DatabaseConnectionManager;
import org.example.Repositiers.UserRepository;
import org.example.Service.UserService;

import java.sql.SQLException;

public class RegistrationApp extends Application {

    private UserService userService;
    private TextField phoneNumberField;
    private TextField displayNameField;
    private TextField emailField;

    public RegistrationApp() {
        DatabaseConnectionManager myConnection = new DatabaseConnectionManager();
        UserRepository userRepository = new UserRepository(myConnection.getMyConnection());
        userService = new UserService(userRepository);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Registration");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label phoneNumberLabel = new Label("Phone Number:");
        phoneNumberField = new TextField();
        grid.add(phoneNumberLabel, 0, 0);
        grid.add(phoneNumberField, 1, 0);

        Label displayNameLabel = new Label("Display Name:");
        displayNameField = new TextField();
        grid.add(displayNameLabel, 0, 1);
        grid.add(displayNameField, 1, 1);

        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 3);

        registerButton.setOnAction(e -> handleRegistration());

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleRegistration() {
        String phoneNumber = phoneNumberField.getText();
        String displayName = displayNameField.getText();
        String email = emailField.getText();

        if (phoneNumber.isEmpty() || displayName.isEmpty() || email.isEmpty()) {
            showAlert("User Registration", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        if (!isEmailValid(email) || !isPhoneNumberValid(phoneNumber)) {
            showAlert("User Registration", "Please enter a valid phone number or email", Alert.AlertType.ERROR);
            return;
        }

        User newUser = new User(phoneNumber, displayName, email, "securepassword", "Female", "Canada", new java.util.Date(), "A short bio", "Online", "Available");

        try {
            if (userService.existsById(newUser.getPhoneNumber())) {
                showAlert("User Registration", "User Already Exists", Alert.AlertType.WARNING);
            } else {
                userService.registerUser(newUser);
                showAlert("User Registration", "User Added Successfully", Alert.AlertType.INFORMATION);
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("User Registration", "Registration Failed", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        phoneNumberField.clear();
        displayNameField.clear();
        emailField.clear();
    }

    private boolean isEmailValid(String email) {
        // Implement email validation logic (e.g., regex or library)
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Implement phone number validation logic (e.g., regex)
        return phoneNumber.matches("[0-9]+");
    }

}
