module server {

    exports org.Server;
    exports org.Server.GUI.Controllers;
    opens org.Server.GUI.Controllers;
    opens org.Server.ServerModels.ServerEntities to javafx.base;

    requires java.rmi;
    requires java.sql;
    requires shared;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires chatter.bot.api;
    requires commons.dbcp2;
    requires c3p0;

}