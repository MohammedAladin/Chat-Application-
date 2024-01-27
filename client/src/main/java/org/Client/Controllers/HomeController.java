package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.Client.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    public BorderPane getParentPane() {
        return parentPane;
    }

    @FXML
    private BorderPane parentPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().selectedMenuItemProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue.equals("home")){
                parentPane.setCenter(Model.getInstance().getViewFactory().showHomeIcon());
            }
        });

    }
}
