package org.Client.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {


    public ViewFactory(){}
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        sceneMaker(loader);
    }
    public void showRegisterWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
        sceneMaker(loader);
    }
    private void sceneMaker(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();

        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Chat App");
        stage.show();
    }

}
