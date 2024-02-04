package org.Server.RepoInterfaces;

import org.Server.ServerModels.ServerEntities.User;
import SharedEnums.StatusEnum;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface UserRepoInterface extends Repository<User,Integer>{
    User findByPhoneNumber(String phoneNumber) throws SQLException;
    void update(Integer id, String fieldName, String value) throws SQLException ;
    void updateStatus(String phoneNumber, StatusEnum status) throws SQLException;
    void updateLoginDate(String phoneNumber, Timestamp lastLogin) throws SQLException;
    void updateUserImage(int i,byte[] image) throws SQLException;
    public void updateDateOfBirth(Integer id, Date dof) throws SQLException;

}
