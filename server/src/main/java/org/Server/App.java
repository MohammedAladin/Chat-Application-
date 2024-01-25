package org.Server;

import Interfaces.RemoteLoginService;
import Interfaces.RemoteRegistrationService;
import Interfaces.ServiceFactoryI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.Repository.UserRepository;
import org.Server.Service.Factories.ServiceFactory;
import org.Server.Service.User.UserService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App extends Application {
    private Stage stage;
    public static void main(String[] args) {
        launch(App.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxml/ServerGUI.fxml"));
        sceneMaker(loader);

    }

    private void sceneMaker(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(stage==null){
            stage = new Stage();
            stage.setMinWidth(400);
            stage.setMinHeight(600);
        }
        stage.setScene(scene);
        stage.setTitle("Chat App");
        stage.show();
        stage.setResizable(true);
    }
}
