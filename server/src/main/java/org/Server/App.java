package org.Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private Stage stage;
    public static void main(String[] args) {
        launch(App.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxml/ServerHome.fxml"));
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
