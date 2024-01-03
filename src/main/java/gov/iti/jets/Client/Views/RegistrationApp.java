package gov.iti.jets.Client.Views;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;


import gov.iti.jets.Client.Model.User;
import gov.iti.jets.Client.Repositiers.DatabaseConnectionManager;
import gov.iti.jets.Client.Repositiers.UserRepository;
import gov.iti.jets.Client.Service.UserService;

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
        grid.setPadding(new Insets(20, 20, 20, 20));

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

        User newUser = new User(
                phoneNumber, displayName, email,
                "securepassword",
                "Female", "Canada", new java.util.Date(), "A short bio",
                "Online", "Available"
        );

        try {
            boolean userExists = userService.existsById(newUser.getPhoneNumber());
            if (!userExists) {
                userService.registerUser(newUser);
                System.out.println("User Added Successfully");

               
                showAlert("User Registration", "User Added Successfully", null);

            } else {
                System.out.println("User Already exists");

                showAlert("User Registration", "User Already Exists", AlertType.WARNING);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            showAlert("User Registration", "Registration Failed", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
