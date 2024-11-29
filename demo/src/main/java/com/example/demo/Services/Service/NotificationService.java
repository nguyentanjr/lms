package com.example.demo.Services.Service;

import com.example.demo.Model.Notification;
import com.example.demo.Model.NotificationMessage;

import java.util.List;

public interface NotificationService {
    void sendNotificationToUser(String username, String message);
    List<Notification> getAllNotificationByUserName();
    List<Notification> getAllNotification();
    int countUnreadNotification();
    void markAsRead();
}
