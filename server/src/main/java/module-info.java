module server {
    exports org.Server.Service;
    exports org.Server.Service.User;
    exports org.Server.Service.Factories;


    requires java.rmi;
    requires java.sql;
    requires shared;
    requires mysql.connector.java;
    requires java.naming;
    requires javafx.controls;

}