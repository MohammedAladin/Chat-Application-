package org.Server;
import Model.DTO.AttachmentDto;
import Model.DTO.UserRegistrationDTO;
import org.Server.Service.Messages.AttachmentService;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.Date;



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
