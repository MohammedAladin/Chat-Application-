module shared {
    exports Model.DTO;
    exports Exceptions;
    exports Interfaces.RmiServices;
    exports Interfaces.CallBacks.Client;
    exports Interfaces.CallBacks.Server;
    exports SharedEnums;
    exports Model.Enums;

    requires java.rmi;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;

}