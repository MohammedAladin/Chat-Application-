package org.example;

import Interfaces.Registration;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Models.Model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App extends Application
{

    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
