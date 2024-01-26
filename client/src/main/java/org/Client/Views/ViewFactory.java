package org.Client.Views;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getServerAnnouncement() {
        return serverAnnouncement.get();
    }

    public StringProperty serverAnnouncementProperty() {
        return serverAnnouncement;
    }

    public void setServerAnnouncement(String serverAnnouncement) {
        this.serverAnnouncement.set(serverAnnouncement);
    }

    StringProperty serverAnnouncement = new SimpleStringProperty();
    public ViewFactory(){}
    public void showRegisterWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Register.fxml"));
        sceneMaker(loader);
    }
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Login.fxml"));
        sceneMaker(loader);
    }
    public void showRegistrationWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Register.fxml"));
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
        stage.setTitle("Chat ServerApplication");
        stage.show();
        stage.setResizable(true);
    }

}
