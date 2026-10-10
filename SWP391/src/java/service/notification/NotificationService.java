package service.notification;

import DAO.Notification.NotificationDAO;
import model.entity.Notification;

import java.util.List;

public class NotificationService {
    private final NotificationDAO notificationDAO;

    public NotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    public List<Notification> getUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    public int getUnreadCount(int userId) {
        return notificationDAO.getUnreadCount(userId);
    }

    public boolean markAllAsRead(int userId) {
        return notificationDAO.markAllAsRead(userId);
    }

    public boolean createNotification(int userId, String title, String message, String type, Integer refId) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setReferenceId(refId);
        n.setRead(false);
        return notificationDAO.addNotification(n);
    }
}
