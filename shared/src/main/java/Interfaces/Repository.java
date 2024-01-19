package Interfaces;
import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T,ID> {
    void save(T entity) throws SQLException;

    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void deleteById(ID id) throws SQLException;
}
