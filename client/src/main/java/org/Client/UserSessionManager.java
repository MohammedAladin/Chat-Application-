package org.Client;

import java.io.*;
import java.util.Properties;

public class UserSessionManager {
    private static final String USER_INFO_FILE = "user_info.properties";

    public static void saveUserInfo(String phoneNumber, String password) {
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream(USER_INFO_FILE)) {

            prop.setProperty("phoneNumber", phoneNumber);
            prop.setProperty("password", password);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static String[] loadUserInfo() {
        String[] userInfo = new String[2];
        try (InputStream input = new FileInputStream(USER_INFO_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            userInfo[0] = prop.getProperty("phoneNumber");
            userInfo[1] = prop.getProperty("password");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static boolean checkFileExisted(){
        File file = new File(USER_INFO_FILE);
        return file.exists();
    }

    public static void deleteUserInfo() {
        File file = new File(USER_INFO_FILE);
        if (file.exists()) {
            boolean b= file.delete();
            if(b) System.out.println("User Info Has Been Deleted Successfully");
        }
    }
}
