package org.Client.Views;

import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import Model.DTO.ParticipantDto;
import javafx.animation.FadeTransition;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.Client.Controllers.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.Client.Models.Model;


public class ViewFactory {
    Stage stage;

    private StringProperty selectedMenuItem = new SimpleStringProperty("");

    private BorderPane home;

    Popup popup;
    Popup addGroupPopup;


    private Node homeIcon;
    Popup addContactPopup;
    Popup notificationPopup;

    ChatUserController chatUserController;
    Button notiButton;

    Popup stylePopup;

    private ObjectProperty<ContactDto> selectedChat = new SimpleObjectProperty<>();
    private String phoneNumber;


    public ChatUserController getChatUserController() {
        return chatUserController;
    }

    public void setChatUserController(ChatUserController chatUserController) {
        this.chatUserController = chatUserController;
    }

    public String getImageChange() {
        return imageChange.get();
    }

    public StringProperty imageChangeProperty() {
        return imageChange;
    }

    public void setImageChange(String imageChange) {
        this.imageChange.set(imageChange);
    }

    private StringProperty imageChange = new SimpleStringProperty();


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
            if (button != null) {
                Stage loginStage = (Stage) button.getScene().getWindow();
                loginStage.close();
            }
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

    public void showChatArea(ContactDto selectedChat) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/PrivateChat.fxml"));
        chatUserController = new ChatUserController();
        chatUserController.setName(selectedChat.getContactName());
        chatUserController.setImage(selectedChat.getContactImage());
        chatUserController.setChatID(selectedChat.getChatId());
        chatUserController.setBioString(selectedChat.getBio());
        fxmlLoader.setController(chatUserController);
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/EditProfilenew.fxml"));
        try {
            Node root = fxmlLoader.load();
            //root.getStylesheets().add(getClass().getResource("/ClientStyles/profile.css").toExternalForm());


            return root;
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

    public AnchorPane showUserCard(ContactDto user, String phoneNumber, boolean isFriend) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/userCard.fxml"));
        try {
            UserCardController controller = new UserCardController();
            controller.setName(user.getContactName());
            controller.setPhoneNumber(phoneNumber);
            controller.setImage(user.getContactImage());
            controller.setFriend(isFriend);
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
        //addGroupPopup.setAutoHide(true);

    }

    public void showLogoutPopup(Button logoutBtn) {
    }

    public void showGroupChatArea(ChatDto chat) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/PrivateChat.fxml"));
        chatUserController= new ChatUserController();
        chatUserController.setName(chat.getChatName());
        chatUserController.setImage(chat.getChatImage());
        chatUserController.setChatID(chat.getChatID());
        List<ParticipantDto> participants = chat.getParticipants();
        chatUserController.setParticipants(participants);
        StringBuilder chatParticipants = new StringBuilder();
        for (ParticipantDto participant : participants) {
            chatParticipants.append(participant.getParticipantName()).append(", ");
        }
        chatParticipants.delete(chatParticipants.length() - 2, chatParticipants.length());
        chatUserController.setBioString(chatParticipants.toString());
        fxmlLoader.setController(chatUserController);
        try {
            home.setCenter(fxmlLoader.load());
            System.out.println("function implemeneted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notify(String message) {
        playNotificationSound();
        showNoti(message);

    }

    private void playNotificationSound() {
        String audioFilePath = Objects.requireNonNull(getClass().getResource("/Audio/ding.mp3")).toExternalForm();
        Media audio = new Media(audioFilePath);
        MediaPlayer mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.play();
    }

    public Button getNotiButton() {
        return notiButton;
    }

    public void setNotiButton(Button notiButton) {
        this.notiButton = notiButton;
    }

    public void showNoti(String message){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/NotificationPop.fxml"));
        NotificationPopController controller = new NotificationPopController();
        controller.setMsg(message);
        fxmlLoader.setController(controller);
        Bounds buttonBounds = notiButton.localToScreen(notiButton.getBoundsInLocal());
        Popup popupe = new Popup();
        double popupX = buttonBounds.getMaxX() - 30;
        double popupY = buttonBounds.getMinY();
        try {
            VBox root = fxmlLoader.load();
            popupe = new Popup();
            popupe.getContent().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        popupe.show(home.getScene().getWindow(), popupX, popupY);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), popupe.getScene().getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        //Play the FadeTransition
        ft.play();

    }

    public void showStylePopup(Button styleBtn) {
        Bounds buttonBounds = styleBtn.localToScreen(styleBtn.getBoundsInLocal());
        StyleController styleController = new StyleController();
        double popupX = buttonBounds.getMaxX() - 30;
        double popupY = buttonBounds.getMinY();
        if (stylePopup == null) {
            stylePopup = new Popup();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/StylePopup.fxml"));
            fxmlLoader.setController(styleController);

            // Set the position of the popup next to the button

            try {
                VBox root = fxmlLoader.load();
                stylePopup.getContent().add(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        stylePopup.show(styleBtn.getParent(), popupX, popupY);
        stylePopup.setAutoHide(true);
    }
}
