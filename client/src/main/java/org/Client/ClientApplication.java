package org.Client;

import Model.DTO.UserLoginDTO;
import javafx.application.Application;
import javafx.stage.Stage;
import org.Client.Controllers.LoginController;
import org.Client.Controllers.RemoteServiceHandler;
import org.Client.Models.Model;

import java.rmi.RemoteException;


public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) {
        if(UserSessionManager.checkFileExisted()){
            String[] userInfo = UserSessionManager.loadUserInfo();
            try {
                boolean result =RemoteServiceHandler.getInstance().getRemoteUserService().signInUser(new UserLoginDTO(userInfo[0], userInfo[1]));
                new LoginController().handleLoginResult(result, userInfo[0]);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else

            Model.getInstance().getViewFactory().showLoginWindow();
    }
    public static void main(String[] args) {
        launch(ClientApplication.class, args);
    }
}
