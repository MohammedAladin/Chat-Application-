package org.Server.GUI.Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.Server.ServerApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.Server.ServerApplication.*;

public class ServerSideBarController implements Initializable {
    @FXML
    public HBox home_icon;
    @FXML
    public HBox statistics_icon;
    @FXML
    public HBox database_icon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip.install(home_icon, new Tooltip("Home"));
        Tooltip.install(statistics_icon, new Tooltip("Statistics"));
        Tooltip.install(database_icon, new Tooltip("Database"));
    }

    @FXML
    private void showStatistics () {
        ServerApplication.sideBar.setCenter(statistics);
    }

    @FXML
    private void showHome () {
        ServerApplication.sideBar.setCenter(home);
    }

    @FXML
    private void showDatabase() {
        ServerApplication.sideBar.setCenter(database);
    }
}
