package Interfaces.RmiServices;

import Model.DTO.BlockedContactDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BlockedContactsInterface extends Remote {

    void blockContact(BlockedContactDTO blockedContactDTO) throws RemoteException;

    void unblock(BlockedContactDTO blockedContactDTO) throws RemoteException;

    Integer existsByDTO(BlockedContactDTO blockedContactDTO) throws RemoteException;
}
