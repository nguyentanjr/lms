package com.example.demo.Services.ServiceImpls;

import com.example.demo.Model.Notification;
import com.example.demo.Model.NotificationMessage;
import com.example.demo.Respository.NotificationRepository;
import com.example.demo.Services.Service.NotificationService;
import com.example.demo.Services.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserService userService;

    public void sendNotificationToUser(String username, String message) {
        Notification notification = new Notification(username, message);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/private",
                notification
        );
    }

    public List<Notification> getAllNotificationByUserName() {
        long userId = userService.getUserId();
        String username = userService.findUserNameByUserId(userId);
        return notificationRepository.getAllNotification(username);
    }

    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    public int countUnreadNotification() {
        List<Notification> notifications = getAllNotification();
        return notifications.stream().filter(notification -> !notification.isRead()).toList().size();
    }

    public void markAsRead() {
        List<Notification> notifications = getAllNotification();
        for (Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}
