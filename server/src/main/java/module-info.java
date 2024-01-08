module server {
    exports org.Server.Service;
    requires java.rmi;
    requires java.sql;
    requires shared;
    requires mysql.connector.java;
    requires java.naming;
    requires javafx.controls;

}