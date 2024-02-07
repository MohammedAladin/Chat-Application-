package org.Client.Models;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;
import Model.DTO.NotificationDTO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.paint.Color;
import Model.DTO.Style;
import org.Client.Controllers.StyleController;
import org.Client.Views.ViewFactory;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private final ViewFactory viewFactory;
    private static Model model;
    private boolean contactExists;
    private Integer clientId;
    private String phoneNumber;
    private Date birthDate;
    private String country;
    private String email;
    private String gender;

    private StyleController styleController;
    private Style style = new Style(12,new String[]{"normal","normal" ,"none"},"#000000","#EFF6FC");
    private Style defaultStyle = new Style(12,new String[]{"normal","normal" ,"none"},"#000000","#EFF6FC");

    public Style getDefaultStyle() {
        return defaultStyle;
    }

    public StyleController getStyleController() {
        return styleController;
    }

    public void setStyleController(StyleController styleController) {
        this.styleController = styleController;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private ObservableList<ChatDto> groupList = javafx.collections.FXCollections.observableArrayList();
    private ObservableList<ContactDto> contacts = javafx.collections.FXCollections.observableArrayList();

    public ObservableMap<Integer, ObservableList<MessageDTO>> getPrivateChats() {
        return privateChats.get();
    }

    public MapProperty<Integer, ObservableList<MessageDTO>> privateChatsProperty() {
        return privateChats;
    }

    public void setPrivateChats(Integer ChatID, List<MessageDTO> privateChats) {
        this.privateChats.put(ChatID, FXCollections.observableArrayList(privateChats));
        System.out.println("this is the private chat" + privateChats + " " + ChatID);
    }

    private MapProperty<Integer, ObservableList<MessageDTO>> privateChats = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private MapProperty<Integer, ObservableList<MessageDTO>> groupChats = new SimpleMapProperty<>(FXCollections.observableHashMap());

    public void setGroupList(ArrayList<ChatDto> groupList) {
        this.groupList.clear();
        this.groupList.setAll(groupList);
        System.out.println("this is the group list" + groupList);
    }

    private ListProperty<ChatDto> groupListProperty;

    public ListProperty<ChatDto> groupListProperty() {
        if (groupListProperty == null) {
            groupListProperty = new SimpleListProperty<>(getGroupList());
        }
        return groupListProperty;
    }

    public String getName() {
        return name.get();
    }


    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    private StringProperty name = new SimpleStringProperty();

    public ObjectProperty<byte[]> profilePictureProperty() {
        return profilePicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture.get();
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture.set(profilePicture);
    }

    ObjectProperty<byte[]> profilePicture = new SimpleObjectProperty<>();

    private CallBackServicesClient callBackServicesClient;
    private CallBackServicesServer callBackServicesServer;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public ObservableList<ContactDto> getContacts() {
        return contacts;
    }


    public ObservableList<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<NotificationDTO> notifications) {
        this.notifications.clear();
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
        this.contacts.clear();
        this.contacts.addAll(contacts);
        System.out.println(contacts);
    }

    public void setDisplayName(String name) {
        this.name.set(name);

    }


    public ObservableList<ChatDto> getGroupList() {
        System.out.println("this is the group list" + groupList);
        return groupList;

    }

    public void addMessage(MessageDTO messageDTO) {
        if(privateChats.get(messageDTO.getChatID())==null){
            try {
               getCallBackServicesServer().getPrivateChatMessages(messageDTO.getChatID(),getCallBackServicesClient());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            privateChats.get(messageDTO.getChatID()).add(messageDTO);
        }

        String senderName="";
        if(!messageDTO.getSenderID().equals(clientId)){
            boolean isContact = contacts.stream()
                    .anyMatch(contact -> contact.getContactID().equals(messageDTO.getSenderID()));
            if (!isContact) {
               senderName =groupList.stream()
                        .filter(chatDto -> chatDto.getChatID().equals(messageDTO.getChatID()))
                        .findFirst()
                        .map(ChatDto::getChatName)
                        .orElse(null);

            }
            else {
                senderName = contacts.stream()
                        .filter(contact -> contact.getContactID().equals(messageDTO.getSenderID()))
                        .findFirst().get().getContactName();
            }
            getViewFactory().notify("New message from " + senderName);
        }
    }

    public void addGroupMessage(MessageDTO messageDTO) {
        groupChats.get(messageDTO.getChatID()).add(messageDTO);
        System.out.println("this is the message" + messageDTO.getContent());
    }
}
