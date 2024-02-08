package org.Server.Service.Contacts;

import Interfaces.RmiServices.BlockedContactsInterface;
import Model.DTO.BlockedContactDTO;
import org.Server.RepoInterfaces.BlockedContactsRepoInterface;
import org.Server.Repository.BlockedContactsRepository;
import org.Server.Repository.DatabaseConnectionManager;
import org.Server.ServerModels.ServerEntities.BlockedEntity;
import org.Server.ServerModels.ServerEntities.User;
import org.Server.Service.User.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class BlockedContactsService extends UnicastRemoteObject implements BlockedContactsInterface{
    UserService userService;
    private BlockedContactsRepository blockedContactsRepository;
    private ContactService contactService;

    public BlockedContactsService() throws RemoteException {
        this.blockedContactsRepository = new BlockedContactsRepository();
        this.userService = UserService.getInstance();

    }

    public void blockContact (BlockedContactDTO blockedContactDTO) {
        this.contactService = new ContactService();

        User user = userService.existsByPhoneNumber(blockedContactDTO.getBlockedUserPhoneNumber());
        contactService.deleteContact(user.getUserID(), blockedContactDTO.getUserID());
        System.out.println("After deleting the contact");

        try {
            Integer userID = user.getUserID();
            BlockedEntity blockedEntity = new BlockedEntity(blockedContactDTO.getUserID(), userID);
            blockedContactsRepository.save(blockedEntity);
            System.out.println("after saving");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void unblock (BlockedContactDTO blockedContactDTO){
        User blockedUser = userService.existsByPhoneNumber(blockedContactDTO.getBlockedUserPhoneNumber());

        BlockedEntity blockedEntity = new BlockedEntity(blockedContactDTO.getUserID(), blockedUser.getUserID());
        Integer id = blockedContactsRepository.getIdIfBlocked(blockedEntity);
        if (id != -1) {
            try {
                blockedContactsRepository.deleteById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Integer getIdIfUserIsBlocking (BlockedContactDTO blockedContactDTO) {
        User blockedUser = userService.existsByPhoneNumber(blockedContactDTO.getBlockedUserPhoneNumber());
        BlockedEntity blockedEntity = new BlockedEntity(blockedContactDTO.getUserID(), blockedUser.getUserID());
        return blockedContactsRepository.getIdIfBlocked(blockedEntity);
    }
    public Integer existsByDTO (BlockedContactDTO blockedContactDTO) {
        Integer userId = blockedContactDTO.getUserID();
        User user = userService.existsByPhoneNumber(blockedContactDTO.getBlockedUserPhoneNumber());
        if (user != null) {
            int blockedContactID = user.getUserID();
            BlockedEntity blockedEntity = new BlockedEntity(userId, blockedContactID);
            return blockedContactsRepository.existsByBlockedEntity(blockedEntity);
        }
        return -1;
    }

}