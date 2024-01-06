module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;
    requires shared;
    requires java.rmi;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.Contollers;
    exports org.example.Models;
    exports org.example.Views;
}