package org.Client.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import javax.imageio.ImageIO;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public Button home_btn;
    public Button group_btn;
    public Button noti_btn;
    public Button profile_btn;
    public Button settings_btn;
    public FontAwesomeIconView logout_btn;
    public Button addContact_btn;
    public Label userName;
    @FXML
    public Circle imageCircle;


    public String getNameProperty() {
        return nameProperty.get();
    }

    public StringProperty namePropertyProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }

    StringProperty nameProperty = new SimpleStringProperty();



    public void setImagebytes(byte[] imagebytes) {
        this.imagebytes.set(imagebytes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ObjectProperty<byte[]> imagebytes=new SimpleObjectProperty<>();
    String name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Circle circle = new Circle(50,50,50);
        //userImage.setClip(circle);
        nameProperty.bind(Model.getInstance().nameProperty());
        nameProperty.addListener((observableValue, s, t1) -> {
            userName.setText("Welcome, " + t1);
        });
        imagebytes.bind(Model.getInstance().profilePictureProperty());
        imagebytes.addListener((observableValue, bytes, t1) -> {
            if(t1!=null){
                imageCircle.setFill(new ImagePattern(ImageServices.convertToImage(t1)));
            }
            else imageCircle.setFill(new ImagePattern(new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toString())));
        });

        userName.setText("Welcome, " + Model.getInstance().getName());
        settings_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().showPopup(settings_btn);
        });

        addContact_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().showAddContacts(addContact_btn);
        });

        home_btn.setOnAction(e -> {
            if (Model.getInstance().getViewFactory().selectedMenuItemProperty().get().equals("home")) {
                Model.getInstance().getViewFactory().selectedMenuItemProperty().set("");
            }
            Model.getInstance().getViewFactory().selectedMenuItemProperty().set("home");
        });
        profile_btn.setOnAction(e -> {
            if (Model.getInstance().getViewFactory().selectedMenuItemProperty().get().equals("profile")) {
                Model.getInstance().getViewFactory().selectedMenuItemProperty().set("");
            }
            Model.getInstance().getViewFactory().selectedMenuItemProperty().set("profile");
        });

        noti_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().showNotificationPopUp(noti_btn);
        });
        group_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().showAddGroup(group_btn);
        });
    }
}
