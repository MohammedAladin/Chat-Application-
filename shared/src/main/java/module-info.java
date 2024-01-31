module shared {
    exports Model.DTO;
    exports Exceptions;
    exports Interfaces.RmiServices;
    exports Interfaces.CallBacks.Client;
    exports Interfaces.CallBacks.Server;
    exports SharedEnums;

    requires java.rmi;
    requires java.sql;
    requires javafx.base;

}