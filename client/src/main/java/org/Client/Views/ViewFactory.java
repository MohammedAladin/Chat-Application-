package org.Client.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
    Stage stage;
    public ViewFactory(){}
    public void showAuthWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth.fxml"));
        sceneMaker(loader);
    }
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        sceneMaker(loader);
    }
    public void showRegistrationWindow(){
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
