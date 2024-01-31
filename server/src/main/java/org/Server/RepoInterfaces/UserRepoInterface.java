package org.Server.RepoInterfaces;

import org.Server.ServerModels.ServerEntities.User;
import SharedEnums.StatusEnum;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface UserRepoInterface extends Repository<User,Integer>{
    User findByPhoneNumber(String phoneNumber) throws SQLException;
    void updateStatus(String phoneNumber, StatusEnum status) throws SQLException;
    void update(String phoneNumber, String fieldName, String value) throws SQLException;
    void updateLoginDate(String phoneNumber, Timestamp lastLogin) throws SQLException;
}
