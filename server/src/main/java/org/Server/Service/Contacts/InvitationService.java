package org.Server.Service.Contacts;

import org.Server.ServerModels.ServerEntities.User;
import org.Server.ServerModels.ServerEntities.UserNotification;
import org.Server.Repository.UserNotificationRepository;
import org.Server.Service.User.UserService;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class InvitationService {

    private final UserService userService;
    private final UserNotificationRepository userNotificationRepository;
    public InvitationService(){
        this.userService = UserService.getInstance();
        this.userNotificationRepository = UserNotificationRepository.getInstance();
    }
    public boolean sendInvitation(int loggedUser, String contactPhoneNumber) throws NullPointerException{
        //get the logged user and check if there is a logged user
        User invitedUser;
        invitedUser = userService.existsByPhoneNumber(contactPhoneNumber);

        if(invitedUser==null){
            return false;
        }
        //try to send invitation from logged user to the new contact
        try {

            System.out.println("loggedUserID... " + loggedUser);
            System.out.println("invitedUserID... " + invitedUser.getUserID());

            UserNotification notification = new UserNotification(loggedUser, invitedUser.getUserID(),
                   "Please Accept My Invitation", new Timestamp(System.currentTimeMillis()));
            userNotificationRepository.save(notification);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Server Error...." + e.getMessage());
        }
    }
    public void deleteInvitation(Integer UserInvitationIdToBeDeleted,Integer userID) {

        Optional<Integer> invitationId;
        List<UserNotification> userNotificationList;
        userNotificationList = getInvitations(userID);

        invitationId = userNotificationList.stream()
                .filter(e-> e.getSenderID() == UserInvitationIdToBeDeleted)
                        .map(UserNotification::getNotificationID).findAny();

        if(invitationId.isPresent()){
            try {
                System.out.println("NotificationID " + invitationId.get());
                userNotificationRepository.deleteById(invitationId.get());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public List<UserNotification> getInvitations(Integer userID){
        List<UserNotification> notificationList;
        try {
            notificationList = userNotificationRepository.getInvitationsForUser(userID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notificationList;
    }



}
