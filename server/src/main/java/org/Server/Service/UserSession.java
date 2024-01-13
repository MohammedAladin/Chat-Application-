package org.Server.Service;
import Model.DTO.UserLoginDTO;
public class UserSession {
    private static UserLoginDTO currentUser;

    public static UserLoginDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserLoginDTO user) {
        currentUser = user;
    }

    public static void clearSession() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
