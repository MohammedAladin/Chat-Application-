module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;
    requires shared;
    requires java.rmi;
    requires server;

    opens org.Client to javafx.fxml;
    exports org.Client;
    exports org.Client.Contollers;
    exports org.Client.Models;
    exports org.Client.Views;
}