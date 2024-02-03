package org.Client;

import Model.DTO.UserLoginDTO;
import javafx.application.Application;
import javafx.stage.Stage;
import org.Client.Controllers.LoginController;
import org.Client.Models.Model;

import java.rmi.RemoteException;


public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) {
//        if (UserSessionManager.checkFileExisted()) {
//            String[] userInfo = UserSessionManager.loadUserInfo();
//            System.out.println("found");
//            UserLoginDTO userLogin = new UserLoginDTO(userInfo[0], userInfo[1]);
//            new LoginController().handleLoginResult(true, userInfo[0], userInfo[1]);
//        }
//
//        else
         Model.getInstance().getViewFactory().showLoginWindow();

    }
    public static void main(String[] args) {
        launch(ClientApplication.class, args);
    }
}
