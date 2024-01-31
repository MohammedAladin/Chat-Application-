module shared {
    exports Model.DTO;
    exports Exceptions;
    exports Interfaces.RmiServices;
    exports Interfaces.CallBacks.Client;
    exports Interfaces.CallBacks.Server;

    requires java.rmi;
    requires java.sql;
    requires javafx.base;

}