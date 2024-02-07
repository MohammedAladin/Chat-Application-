package org.Server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerApplication extends Application {
    public static BorderPane sideBar;
    public static Node home;
    public static Node statistics;
    public static Node database;
    public static void main(String[] args) {
        launch(ServerApplication.class, args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        // Statistics
        FXMLLoader loader3 = new FXMLLoader (getClass().getResource("/fxml/ServerStatistics.fxml"));
        statistics = loader3.load();

        // Home
        FXMLLoader loader2 = new FXMLLoader (getClass().getResource("/fxml/ServerHome.fxml"));
        home = loader2.load();

        // Database
        FXMLLoader loader4 = new FXMLLoader (getClass().getResource("/fxml/ServerDatabase.fxml"));
        database = loader4.load();

        // sideBar
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxml/ServerSideBar.fxml"));
        sideBar = loader.load();
        sideBar.setCenter(home);

        // Scene & Stage
        Scene scene = new Scene(sideBar);
        stage.setScene(scene);
        stage.setTitle("Chat ServerApplication");
        stage.show();
        stage.setMinWidth(750);
        stage.setMinHeight(400);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Closing application...");
                Platform.exit();
            }
        });

    }
}
