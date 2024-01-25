module server {
    exports org.Server.Service;
    exports org.Server.Service.User;
    exports org.Server.Service.Factories;
    exports org.Server.Service.Contacts;
    exports org.Server;
    exports org.Server.GUI.Controllers;
    opens org.Server.GUI.Controllers;

    requires java.rmi;
    requires java.sql;
    requires shared;
    requires mysql.connector.java;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;

}