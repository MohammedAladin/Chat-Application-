package org.Server.RepoInterfaces;

import org.Server.ServerModels.ServerEntities.Message;

import java.sql.SQLException;

public interface MessageRepoInterface extends Repository<Message, Integer>{
    Integer getLastInsertedId() throws SQLException;
}
