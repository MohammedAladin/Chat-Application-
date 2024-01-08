package org.Client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.Client.Models.Model;

public class App extends Application
{

    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
