package org.Server.RepoInterfaces;

import org.Server.ServerModels.ServerEntities.Chat;

import java.util.List;

public interface ChatRepoInterface extends Repository<Chat,Integer>{
    List<Chat> getAllPrivateChats(Integer userID, Integer phoneNumber);
    List<Chat> getGroupChats(Integer userID);
}
