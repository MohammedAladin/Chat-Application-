package Interfaces;

import Model.DTO.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageService extends Remote {
    void sendMessage(MessageDTO messageDTO) throws RemoteException;
}
