package org.Server;

public class Main {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname","192.168.62.164");
        ServerApplication.main(args);
    }
}
