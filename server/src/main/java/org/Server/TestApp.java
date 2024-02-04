package org.Server;
import Model.DTO.ChatDto;
import Model.DTO.MessageDTO;
import Model.Enums.UserField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Service.User.UserService;

import java.util.HashMap;
import java.util.Map;


public class TestApp {

    public static void main(String[] args) {

       UserService userService = UserService.getInstance();
       User user = userService.existsByPhoneNumber("01118925775");

        Map<String, String> up = new HashMap<>();
        up.put(UserField.NAME.getFieldName(), "Alaa Eldin Ibraheem ");
        up.put(UserField.COUNTRY.getFieldName(), "Egypt");
       userService.updateUserInfo(user.getUserID(), up);



    }


}
