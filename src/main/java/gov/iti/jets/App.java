package gov.iti.jets;

import gov.iti.jets.Client.Views.RegistrationApp;

public class App 
{
    public static void main( String[] args )
    {
        System.setProperty("javafx.platform", "javafx.swing");

        javafx.application.Application.launch(RegistrationApp.class, args);
    }
}
