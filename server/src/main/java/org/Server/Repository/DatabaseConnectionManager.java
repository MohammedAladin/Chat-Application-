package org.Server.Repository;


import org.apache.commons.dbcp2.BasicDataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
public class DatabaseConnectionManager {
    private static final String PROPERTIES_FILE_PATH = "dp.properties";
    private static DatabaseConnectionManager instance;
    static ComboPooledDataSource comboPooledDataSource = null;

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
            OutputStream oStream = new FileOutputStream(PROPERTIES_FILE_PATH);
            prop.setProperty("URL", "jdbc:mysql://localhost:3306/ChatApplicationDB");
            prop.setProperty("User", "root");
            prop.setProperty("Password", "password");

            prop.store(oStream, null);
            oStream.close();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }
    }

//    private BasicDataSource createBasicDataSource(){
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        ds.setUrl("jdbc:mysql://localhost:3306/ChatApplicationDB");
//        ds.setUsername("user");
//        ds.setPassword("password");
//        ds.setMinIdle(5);
//        ds.setMaxIdle(10);
//        ds.setMaxOpenPreparedStatements(100);
//        return ds;
//    }
    private ComboPooledDataSource createCompoPooledDataSource() {

        if(isPropFileGenerated()){
          generatePropFile();
        }

        Properties properties = new Properties();
        InputStream propFile = null;
        try {
            propFile = new FileInputStream(PROPERTIES_FILE_PATH);
            properties.load(propFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(properties.getProperty("URL"));
        comboPooledDataSource.setUser(properties.getProperty("User"));
        comboPooledDataSource.setPassword(properties.getProperty("Password"));

        comboPooledDataSource.setMinPoolSize(5);
        comboPooledDataSource.setAcquireIncrement(3);
        comboPooledDataSource.setMaxPoolSize(50);

        return comboPooledDataSource;
    }

    private boolean isPropFileGenerated() {
        File file = new File(PROPERTIES_FILE_PATH);
        return file.exists();
    }

    public Connection getMyConnection() {
        ComboPooledDataSource ds = createCompoPooledDataSource();

        try {
            return ds.getConnection();
        } catch (SQLException e) {
            System.out.println("Can not create connection... ");
            e.printStackTrace();
            return null;
        }

    }
}