package org.Server.Service;
import Model.DTO.UserLoginDTO;
import Model.Entities.User;

public class UserSession {
    private static User currentUser;
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void clearSession() {
        currentUser = null;
    }
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
