package org.Server.RepoInterfaces;

import org.Server.ServerModels.ServerEntities.BlockedEntity;

import java.sql.SQLException;
import java.util.List;

public interface BlockedContactsRepoInterface extends Repository <BlockedEntity, Integer>{
    List<Integer> findBlockedContactsByUserID(Integer userID) throws SQLException;
    Integer existsByBlockedEntity (BlockedEntity blockedEntity) throws SQLException;
}
