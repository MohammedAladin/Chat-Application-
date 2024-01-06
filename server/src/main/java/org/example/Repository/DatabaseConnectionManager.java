package org.example.Repository;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private static final String propPath = "/media/mohamed/01D3829D52880A80/ChatApplication/MyApp/Client/src/main/resources/dp.properties";
    private static DatabaseConnectionManager instance;

    private DatabaseConnectionManager() {
    }

    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    private void generatePropFile() {
        Properties prop = new Properties();

        try {
            OutputStream oStream = new FileOutputStream(propPath);
            prop.setProperty("URL", "jdbc:mysql://localhost:3306/ChatApplicationDB");
            prop.setProperty("User", "root");
            prop.setProperty("Password", "password");
            prop.store(oStream, null);
            oStream.close();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }
    }

    public DataSource createDataSource() {
        if (!isPropFileGenerated()) {
            generatePropFile();
        }

        Properties prop = new Properties();
        MysqlDataSource mysqlDS = new MysqlDataSource();

        try {
            InputStream iStream = new FileInputStream(propPath);
            prop.load(iStream);
            mysqlDS.setURL(prop.getProperty("URL"));
            mysqlDS.setUser(prop.getProperty("User"));
            mysqlDS.setPassword(prop.getProperty("Password"));
            iStream.close();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }

        return mysqlDS;
    }

    private boolean isPropFileGenerated() {
        File file = new File(propPath);
        return file.exists();
    }

    public Connection getMyConnection() {
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
