package org.Server.Service;

import Interfaces.RemoteRegistrationService;
import Model.DTO.UserRegistrationDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.time.LocalDate;


public class RegistrationService extends UnicastRemoteObject implements RemoteRegistrationService {
    private final UserService userService;

    public RegistrationService(UserService userService) throws RemoteException {
        super();
        this.userService = userService;
    }

    @Override
    public int registerUser(String phoneNumber, String email,String displayName, String password, String confirmPassword,
                            Date dateOfBirth, String gender, String country)
            throws RemoteException {
        try {
            validateUserInput(phoneNumber, displayName, password, confirmPassword, dateOfBirth, gender, country);

            UserRegistrationDTO userDTO = new UserRegistrationDTO(phoneNumber, email,displayName, password, gender, country, dateOfBirth);

            if (userService.existsById(userDTO.getPhoneNumber())) {
                return 0; // User already exists
            } else {
                userService.registerUser(userDTO);
                return 1; // User added successfully
            }
        } catch (IllegalArgumentException e) {
            return 2;
        } catch (Exception e) {
            return 3;
        }
    }

    private void validateUserInput(String phoneNumber, String displayName, String password, String confirmPassword,
                                   Date dateOfBirth, String gender, String country)
            throws IllegalArgumentException {
        if (dateOfBirth != null && dateOfBirth.toLocalDate()!=null) {
            throw new IllegalArgumentException("Date of birth must be in the past");
        }
        if (phoneNumber == null || displayName == null || password == null || confirmPassword == null
                || gender == null || country == null
                || phoneNumber.isEmpty() || displayName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || gender.isEmpty() || country.isEmpty()) {
            throw new IllegalArgumentException("Please fill in all fields");
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        if (!isPhoneNumberValid(phoneNumber)) {
            throw new IllegalArgumentException("Please enter a valid phone number");
        }

        if (dateOfBirth == null || dateOfBirth.toLocalDate()==null) {
            throw new IllegalArgumentException("Date of birth must be in the past");
        }

        // You can add more specific validation for gender and country if needed
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("[0-9]+");
    }
}
