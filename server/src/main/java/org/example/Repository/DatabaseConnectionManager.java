package org.example.Repository;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnectionManager {
    static String propPath = "server/src/main/resources/dp.properties";

    public  void generatePropFile() {
        Properties prop = new Properties();

        try (OutputStream oStream = new FileOutputStream(propPath)) {

            prop.setProperty("URL", "jdbc:mysql://localhost:3306/ChatApplicationDB");
            prop.setProperty("User", "root");
            prop.setProperty("Password", "nada2002");

            prop.store(oStream, null);

        } catch (IOException e) {
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }
    }
    public  DataSource createDataSource() {
        generatePropFile();
        Properties prop = new Properties();
        MysqlDataSource mysqlDS = new MysqlDataSource();

        try (InputStream iStream = new FileInputStream(propPath)) {

            prop.load(iStream);
            mysqlDS.setURL(prop.getProperty("URL"));
            mysqlDS.setUser(prop.getProperty("User"));
            mysqlDS.setPassword(prop.getProperty("Password"));

        } catch (IOException e) {
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }

        return mysqlDS;
    }
    public  Connection getMyConnection() {
        DataSource ds = createDataSource();
        
        Connection con = null;

        try {
            con = ds.getConnection();
            return con;
        } catch (SQLException e) {
            System.out.println("Can not create connection... ");
            e.printStackTrace();
            return null;
        }
    }
}
