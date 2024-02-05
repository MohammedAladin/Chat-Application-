package org.Server;
import Model.DTO.AttachmentDto;
import Model.DTO.ChatDto;
import Model.DTO.MessageDTO;
import Model.DTO.UserRegistrationDTO;
import Model.Enums.UserField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Service.Messages.AttachmentService;
import org.Server.Service.Messages.MessageServiceImpl;
import org.Server.Service.User.RegistrationService;
import org.Server.Service.User.UserService;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


public class TestApp {

    public static void main(String[] args) throws RemoteException {
        String phoneNumber = "1234512345";
        String displayName = "John Doe2";
        String emailAddress = "john.doe2@example.com";
        String passwordHash = "hashedPassword123";
        String gender = "Male";
        String country = "USA";
        Date dateOfBirth = Date.valueOf("1990-01-01");
        byte[] profilePic = null;


        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(
                phoneNumber,
                displayName,
                emailAddress,
                passwordHash,
                gender,
                country,
                dateOfBirth,
                profilePic
        );
        File file = new File("/media/mohamed/01D3829D52880A80/ChatApplication/Chat-Application-/server/src/main/resources/images/home.png");
        AttachmentService service = AttachmentService.getInstance();
        AttachmentDto attachmentDto = new AttachmentDto(
                23,
                6,
                file,
                "any"

        );
        System.out.println(service.sendAttachment(attachmentDto));

    }


}
