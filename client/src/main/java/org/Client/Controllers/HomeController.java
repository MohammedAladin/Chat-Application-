package org.Client.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.Client.Models.Model;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public BorderPane getParentPane() {
        return parentPane;
    }

    @FXML
    private BorderPane parentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader includedLoader = new FXMLLoader(getClass().getResource("/ClientFxml/UserMenu.fxml"));
        UserMenuController userMenuController = new UserMenuController();
        System.out.println("the Model got the name" + Model.getInstance().getName());
        userMenuController.setName(Model.getInstance().getName());
        userMenuController.setImagebytes(Model.getInstance().getProfilePicture());
        includedLoader.setController(userMenuController);
        try {
            parentPane.setLeft(includedLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Model.getInstance().getViewFactory().selectedMenuItemProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue.equals("home")) {
                parentPane.setCenter(Model.getInstance().getViewFactory().showHomeIcon());
            } else if (newValue.equals("profile")) {
                parentPane.setCenter(Model.getInstance().getViewFactory().showProfile());
            }
        });

        Platform.runLater(() -> {
            parentPane.getScene().getWindow().setOnCloseRequest(e -> {
                try {
                    Model.getInstance().getCallBackServicesServer().unRegister(Model.getInstance().getClientId(), Model.getInstance().getPhoneNumber());
                    Platform.exit();
                    System.exit(0);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });

    }
}
