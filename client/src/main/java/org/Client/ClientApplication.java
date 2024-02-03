package org.Client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.Client.Models.Model;


public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
    public static void main(String[] args) {
        launch(ClientApplication.class, args);
    }
}
