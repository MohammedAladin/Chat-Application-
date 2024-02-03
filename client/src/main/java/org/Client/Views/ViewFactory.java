package org.Client.Views;

import Model.DTO.ContactDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.Client.Controllers.ChatUserController;
import org.Client.Controllers.HomeController;
import org.Client.Controllers.UserCardController;

import java.io.IOException;

public class ViewFactory {
    Stage stage;

    private StringProperty selectedMenuItem = new SimpleStringProperty("");

    private BorderPane home;

    Popup popup;
    Popup addGroupPopup;


    private Node homeIcon;
    Popup addContactPopup;
    Popup notificationPopup;

    private ObjectProperty<ContactDto> selectedChat = new SimpleObjectProperty<>();
    private String phoneNumber;

    public String getStatus() {
        return Status.get();
    }

    public StringProperty statusProperty() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status.set(status);
    }

    private StringProperty Status = new SimpleStringProperty("online");

    public String getSelectedMenuItem() {
        return selectedMenuItem.get();
    }


    public StringProperty selectedMenuItemProperty() {
        return selectedMenuItem;
    }

    public void setSelectedMenuItem(String selectedMenuItem) {
        this.selectedMenuItem.set(selectedMenuItem);
    }

    public ContactDto getSelectedChat() {
        return selectedChat.get();
    }

    public ObjectProperty<ContactDto> selectedChatProperty() {
        return selectedChat;
    }

    public void setSelectedChat(ContactDto selectedChat) {
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

    public ViewFactory() {
    }

    public void showRegisterWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Register.fxml"));
        sceneMaker(loader);
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Login.fxml"));
        sceneMaker(loader);
    }

    public void showRegistrationWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Register.fxml"));
        sceneMaker(loader);
    }

    public void showHomePage(Button button) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/Home.fxml"));
        HomeController controller = new HomeController();
        try {

            home = fxmlLoader.load();
            Scene scene = new Scene(home);
            Stage loginStage = (Stage) button.getScene().getWindow();
            loginStage.close();
            Stage stage = new Stage();
            stage.setMinWidth(800);
            stage.setMinHeight(800);
            stage.setTitle("Chat App");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/ClientImages/icon.png")));
            stage.setOnCloseRequest(e -> {
                System.exit(0);
            });
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showChatArea() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/PrivateChat.fxml"));
        ChatUserController controller = new ChatUserController();
        controller.setName(selectedChat.get().getContactName());
        controller.setImage(selectedChat.get().getContactImage());
        controller.setChatID(selectedChat.get().getChatId());
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
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stage == null) {
            stage = new Stage();
            stage.setMinWidth(400);
            stage.setMinHeight(600);
        }
        stage.setScene(scene);
        stage.setTitle("Chat ServerApplication");
        stage.show();
        stage.setResizable(true);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Node showProfile() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/editProfile.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showAddContacts(Button button) {
        Bounds buttonBounds = button.localToScreen(button.getBoundsInLocal());
        double popupX = buttonBounds.getMaxX() - 30;
        double popupY = buttonBounds.getMinY();
        if (addContactPopup == null) {
            addContactPopup = new Popup();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/addContact.fxml"));


            // Set the position of the popup next to the button

            try {
                AnchorPane root = fxmlLoader.load();
                addContactPopup.getContent().add(root);
                System.out.println(popupX + "   " + popupY);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        addContactPopup.show(button.getParent(), popupX, popupY);
        addContactPopup.setAutoHide(true);
    }

    public void showNotificationPopUp(Button button) {
        Bounds buttonBounds = button.localToScreen(button.getBoundsInLocal());
        double popupX = buttonBounds.getMaxX() - 30;
        double popupY = buttonBounds.getMinY();

        notificationPopup = new Popup();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/NotificationList.fxml"));


        // Set the position of the popup next to the button

        try {
            AnchorPane root = fxmlLoader.load();
            notificationPopup.getContent().add(root);
            System.out.println(popupX + "   " + popupY);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        notificationPopup.show(button.getParent(), popupX, popupY);
        notificationPopup.setAutoHide(true);

    }

    public AnchorPane showUserCard(ContactDto user, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/UserCard.fxml"));
        try {
            UserCardController controller = new UserCardController();
            controller.setName(user.getContactName());
            controller.setPhoneNumber(phoneNumber);
            controller.setImage(user.getContactImage());
            fxmlLoader.setController(controller);
            AnchorPane card = fxmlLoader.load();
            return card;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddGroup(Button groupBtn) {
        Bounds buttonBounds = groupBtn.localToScreen(groupBtn.getBoundsInLocal());
        double popupX = buttonBounds.getMaxX() - 30;
        double popupY = buttonBounds.getMinY();

        addGroupPopup = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/AddGroup.fxml"));
        try {
            VBox root = loader.load();
            addGroupPopup.getContent().add(root);
            System.out.println(popupX + "   " + popupY);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addGroupPopup.show(groupBtn.getParent(), popupX, popupY);
    }
}
