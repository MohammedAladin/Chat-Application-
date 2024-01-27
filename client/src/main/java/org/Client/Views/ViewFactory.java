package org.Client.Views;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;
import org.Client.Controllers.Chat;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.Client.Controllers.ChatUserController;
import org.Client.Controllers.HomeController;

import java.io.IOException;

public class ViewFactory {
    Stage stage;

    private StringProperty selectedMenuItem = new SimpleStringProperty("");

    private BorderPane home;

    Popup popup;

    private Node homeIcon;

    private ObjectProperty<Chat> selectedChat = new SimpleObjectProperty<>();

    public String getSelectedMenuItem() {
        return selectedMenuItem.get();
    }


    public StringProperty selectedMenuItemProperty() {
        return selectedMenuItem;
    }

    public void setSelectedMenuItem(String selectedMenuItem) {
        this.selectedMenuItem.set(selectedMenuItem);
    }

    public Chat getSelectedChat() {
        return selectedChat.get();
    }

    public ObjectProperty<Chat> selectedChatProperty() {
        return selectedChat;
    }

    public void setSelectedChat(Chat selectedChat) {
        this.selectedChat.set(selectedChat);
    }
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

    public void showHomePage(Button button) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/Home.fxml"));
        try {

            home = fxmlLoader.load();
            Scene scene = new Scene(home);
            Stage loginStage=(Stage) button.getScene().getWindow();
            loginStage.close();
            Stage stage = new Stage();
            stage.setMinWidth(800);
            stage.setMinHeight(800);
            stage.setTitle("Chat App");
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/ClientImages/icon.png" )));
            stage.setOnCloseRequest(e->{
                System.exit(0);
            });
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
           e.printStackTrace();
        }

    }

    public void showChatArea() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/ChatUser.fxml"));
        ChatUserController controller = new ChatUserController();
        controller.setUsername(selectedChat.get().getName());
        controller.setImage(selectedChat.get().getImage());
        fxmlLoader.setController(controller);
        try {
            home.setCenter(fxmlLoader.load());
            System.out.println("function implemeneted");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showPopup(Button button) {
        Bounds buttonBounds = button.localToScreen(button.getBoundsInLocal());
        double popupX = buttonBounds.getMaxX() - 30;
        double popupY = buttonBounds.getMinY();
        if (popup == null) {
            popup = new Popup();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/statusPopUp.fxml"));


            // Set the position of the popup next to the button

            try {
                AnchorPane root = fxmlLoader.load();
                popup.getContent().add(root);
                System.out.println(popupX + "   " + popupY);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        popup.show(button.getParent(), popupX, popupY);
        popup.setAutoHide(true);

    }

    public void closePopUp() {
        popup.hide();
    }

    public Node showHomeIcon() {
        if (homeIcon == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/ChatHomeIcon.fxml"));
            try {
                homeIcon = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return homeIcon;
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
