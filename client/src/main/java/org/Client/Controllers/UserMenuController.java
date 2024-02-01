package org.Client.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.Client.Models.Model;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        settings_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().showPopup(settings_btn);
        });

        addContact_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().showAddContacts(addContact_btn);
        });

        home_btn.setOnAction(e -> {
            if (Model.getInstance().getViewFactory().selectedMenuItemProperty().get().equals("home")) {
                Model.getInstance().getViewFactory().selectedMenuItemProperty().set("");}
                Model.getInstance().getViewFactory().selectedMenuItemProperty().set("home");
        });
        profile_btn.setOnAction(e -> {
            if (Model.getInstance().getViewFactory().selectedMenuItemProperty().get().equals("profile")) {
                Model.getInstance().getViewFactory().selectedMenuItemProperty().set("");}
                Model.getInstance().getViewFactory().selectedMenuItemProperty().set("profile");
        });

        noti_btn.setOnAction(e->{
           Model.getInstance().getViewFactory().showNotificationPopUp(noti_btn);
        });
        group_btn.setOnAction(e ->{
            Model.getInstance().getViewFactory().showAddGroup(group_btn);
        });
    }
}
