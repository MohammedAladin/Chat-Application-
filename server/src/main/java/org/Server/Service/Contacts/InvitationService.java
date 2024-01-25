package org.Server.Service.Contacts;

import Model.Entities.User;
import Model.Entities.UserNotification;
import org.Server.Repository.UserNotificationRepository;
import org.Server.Service.User.UserService;
import org.Server.Service.UserSession;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class InvitationService {

    private final UserService userService;
    private final UserNotificationRepository userNotificationRepository;
    public InvitationService(UserService userService, UserNotificationRepository userNotificationRepository){
        this.userService = userService;
        this.userNotificationRepository = userNotificationRepository;
    }
    public void sendInvitation(User loggedUser, String contactPhoneNumber) throws NullPointerException{
        //get the logged user and check if there is a logged user
        User invitedUser;
        if(loggedUser==null){
            throw new NullPointerException("You Must Be Signed In");
        }
        try {
            invitedUser = userService.existsById(contactPhoneNumber);

            if(invitedUser==null){
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Server Error....");
        }
        //try to send invitation from logged user to the new contact
        try {

            System.out.println("loggedUserID... " + loggedUser.getUserID());
            System.out.println("invitedUserID... " + invitedUser.getUserID());

            UserNotification notification = new UserNotification(loggedUser.getUserID(), invitedUser.getUserID(),
                   "Please Accept My Invitation", new Timestamp(System.currentTimeMillis()));
            userNotificationRepository.save(notification);
        } catch (SQLException e) {
            throw new RuntimeException("Server Error...." + e.getMessage());
        }
    }
    public void deleteInvitation(Integer UserInvitationIdToBeDeleted) {

        Optional<Integer> invitationId;
        List<UserNotification> userNotificationList;
        userNotificationList = getInvitations();

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
    public List<UserNotification> getInvitations(){
        List<UserNotification> notificationList;
        User loggedUser = UserSession.getCurrentUser();
        try {
            notificationList = userNotificationRepository.getInvitationsForUser(loggedUser.getUserID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notificationList;
    }



}
