package org.Server.Service.Contacts;

import Model.DTO.NotificationDTO;
import org.Server.ServerModels.ServerEntities.Notification;
import org.Server.ServerModels.ServerEntities.UserNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {

    public static ArrayList<NotificationDTO> mapToDTOList(List<UserNotification> notifications) {
        ArrayList<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (UserNotification notification : notifications) {
            NotificationDTO notificationDTO = mapToDTO(notification);
            notificationDTOList.add(notificationDTO);
        }

        return notificationDTOList;
    }

    public static NotificationDTO mapToDTO(UserNotification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationID(notification.getNotificationID());
        notificationDTO.setUserID(notification.getReceiverID());
        notificationDTO.setSenderID(notification.getSenderID());
        notificationDTO.setNotificationContent(notification.getNotificationMessage());

        // You can map other fields as needed
        // Example: notificationDTO.setTimestamp(notification.getTimestamp());

        return notificationDTO;
    }
}
