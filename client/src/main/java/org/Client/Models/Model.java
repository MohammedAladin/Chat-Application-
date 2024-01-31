package org.Client.Models;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.ContactDto;
import Model.DTO.NotificationDTO;
import javafx.collections.ObservableList;
import org.Client.Views.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final ViewFactory viewFactory;
    private static Model model;
    private boolean contactExists;
    private Integer clientId;
    private CallBackServicesClient callBackServicesClient;
    private CallBackServicesServer callBackServicesServer;

    public ObservableList<ContactDto> getContacts() {
        return contacts;
    }

    private ObservableList<ContactDto> contacts = javafx.collections.FXCollections.observableArrayList();

    public ObservableList<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<NotificationDTO> notifications) {
        this.notifications.addAll(notifications);
        System.out.println(notifications);
    }

    private ObservableList<NotificationDTO> notifications = javafx.collections.FXCollections.observableArrayList();

    public CallBackServicesClient getCallBackServicesClient() {
        return callBackServicesClient;
    }

    public void setCallBackServicesClient(CallBackServicesClient callBackServicesClient) {
        this.callBackServicesClient = callBackServicesClient;
    }

    public CallBackServicesServer getCallBackServicesServer() {
        return callBackServicesServer;
    }

    public void setCallBackServicesServer(CallBackServicesServer callBackServicesServer) {
        this.callBackServicesServer = callBackServicesServer;
    }


    public Integer getClientId() {
        return clientId;
    }

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;

    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public boolean isContactExists() {
        return contactExists;
    }

    public void setContactExists(boolean exists) {
        this.contactExists = exists;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts.addAll(contacts);
        System.out.println(contacts);
    }
}
