package org.Client.Service;

import Interfaces.CallBacks.Client.CallBackServicesClient;
import Model.DTO.*;
import javafx.application.Platform;
import javafx.stage.DirectoryChooser;
import org.Client.Models.Model;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

public class ClientServicesImp extends UnicastRemoteObject implements CallBackServicesClient {

    public ClientServicesImp() throws RemoteException {
        super();
        startSendingHeartBeatToTheServer();
    }

    public void sendAnnouncement(String message) throws RemoteException {
        Model.getInstance().getViewFactory().serverAnnouncementProperty().set(message);
    }

    @Override
    public void ReceiveMessageFromServer() throws RemoteException {

    }


    @Override
    public void setClientId(Integer clientId) throws RemoteException {
        Model.getInstance().setClientId(clientId);
    }

    @Override
    public Integer getClientId() throws RemoteException {
        return Model.getInstance().getClientId();
    }


    @Override
    public void contactExists(boolean exists) throws RemoteException {
        Model.getInstance().setContactExists(exists);
    }

    @Override
    public void startSendingHeartBeatToTheServer() throws RemoteException {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::sendHeartbeat, 0,1, TimeUnit.SECONDS);

    }
    private void sendHeartbeat(){
        try {
            Model.getInstance().getCallBackServicesServer().receivingHeartBeatsFromClients(Model.getInstance().getClientId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteContact(Integer contactID) throws RemoteException {
        Platform.runLater(()->Model.getInstance().getContacts().removeIf(contactDto -> contactDto.getContactID().equals(contactID)));
    }

    @Override
    public void setNotificationList(ArrayList<NotificationDTO> notificationDTOS) throws RemoteException {
        Model.getInstance().setNotifications(notificationDTOS);
    }

    @Override
    public void deleteNotification(Integer sendingUserID) throws RemoteException {
        Platform.runLater(() -> {
            Model.getInstance().getNotifications().removeIf(notificationDTO -> notificationDTO.getSenderID().equals(sendingUserID));
            System.out.println(Model.getInstance().getNotifications());
        });
    }

    public void setContactList(List<ContactDto> contacts) throws RemoteException {
        Platform.runLater(() -> Model.getInstance().setContacts(contacts));
    }

    @Override
    public void changeStatus(Integer id, String status) throws RemoteException {
        Platform.runLater(() -> {
            Model.getInstance().getContacts().stream().filter(contactDto -> contactDto.getContactID().equals(id)).findFirst().get().setStatus(status);
            System.out.println(id + "changed status to " + status);
            for (ContactDto contactDto : Model.getInstance().getContacts()) {
                System.out.println(contactDto.getContactID() + " " + contactDto.getStatus());
            }
        });
    }

    @Override
    public void setData(UserRegistrationDTO userInfo) throws RemoteException {
        Platform.runLater(() -> {
            Model.getInstance().setPhoneNumber(userInfo.getPhoneNumber());
            Model.getInstance().setDisplayName(userInfo.getDisplayName());
            Model.getInstance().setProfilePicture(userInfo.getProfilePic());
            Model.getInstance().setBirthDate(userInfo.getDateOfBirth());
            Model.getInstance().setEmail(userInfo.getEmailAddress());
            Model.getInstance().setGender(userInfo.getGender());
        });
    }

    @Override
    public String getPhone() throws RemoteException {
        return Model.getInstance().getPhoneNumber();
    }

    @Override
    public void setGroupList(ArrayList<ChatDto> groupChats) throws RemoteException {
        Platform.runLater(() -> Model.getInstance().setGroupList(groupChats));
        System.out.println("Group list set to " + groupChats);
    }

    @Override
    public void updateGroupList(ChatDto newGroup) throws RemoteException {
        Model.getInstance().getGroupList().add(newGroup);
    }

    @Override
    public void setPrivateMessages(ArrayList<MessageDTO> messages, Integer chatId) throws RemoteException {
        Platform.runLater(() -> Model.getInstance().setPrivateChats(chatId, messages));
    }

    @Override
    public void receiveMessage(MessageDTO messageDTO) throws RemoteException {
        Platform.runLater(() -> {
            Model.getInstance().addMessage(messageDTO);
        });
    }

    @Override
    public void notifyClient(String Message) throws RemoteException {
        Platform.runLater(() -> Model.getInstance().getViewFactory().notify(Message));
    }

    @Override
    public void downloadAttachment(byte[] data, String name) throws RemoteException {
        FutureTask<File> futureTask = new FutureTask<>(() -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            return directoryChooser.showDialog(null);
        });
        Platform.runLater(futureTask);

        new Thread(() -> {
            try {
                File directory = futureTask.get();
                if (directory != null) {
                    File file = new File(directory.getAbsolutePath() + "/" + name);
                    try (FileOutputStream fos = new FileOutputStream(file.getPath())) {
                        fos.write(data);
                        System.out.println("File created successfully!");
                        Platform.runLater(() -> Model.getInstance().getViewFactory().notify("File downloaded successfully!"));
                    } catch (IOException e) {
                        System.out.println("Error occurred: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public boolean isImage(byte[] data) {
        try {
            return ImageIO.read(new ByteArrayInputStream(data)) != null;
        } catch (IOException e) {
            return false;
        }
    }


    public String getImageFormat(byte[] data) {
        try (ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(data))) {
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
            if (imageReaders.hasNext()) {
                ImageReader reader = imageReaders.next();
                return reader.getFormatName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
