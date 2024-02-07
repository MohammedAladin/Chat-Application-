package org.Server;
import Exceptions.CustomException;
import Model.DTO.AttachmentDto;
import Model.DTO.BlockedContactDTO;
import Model.DTO.UserRegistrationDTO;
import org.Server.Repository.BlockedContactsRepository;
import org.Server.Repository.UserRepository;
import org.Server.Service.Contacts.BlockedContactsService;
import org.Server.Service.Messages.AttachmentService;
import org.Server.Service.User.RegistrationService;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;


public class TestApp {

    public static void main(String[] args) throws RemoteException {
        String phoneNumber = "1234512345";
        String displayName = "John Doae2";
        String emailAddress = "john.doae2@example.com";
        String passwordHash = "hashedPaassword123";
        String gender = "Male";
        String country = "USA";
        Date dateOfBirth = Date.valueOf("1990-01-01");
        byte[] profilePic = null;

        BlockedContactsService blockedContactsService = new BlockedContactsService();
//        blockedContactsService.unblock(new BlockedContactDTO(36, "112222222"));
    }


}
