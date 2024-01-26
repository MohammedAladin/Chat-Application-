package org.Server.GUI.Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.Server.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.Server.App.home;
import static org.Server.App.statistics;

public class ServerSideBarController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void showStatistics () {
        App.sideBar.setCenter(statistics);
    }

    @FXML
    private void showHome () {
        App.sideBar.setCenter(home);
    }
}
