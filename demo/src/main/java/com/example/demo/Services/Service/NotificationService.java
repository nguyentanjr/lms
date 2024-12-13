package com.example.demo.Services.Service;

import com.example.demo.Model.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotificationToAllUser(String message);
    void sendNotificationToUser(String username, String message);
    List<Notification> getAllNotificationPrivate();
    List<Notification> getAllNotificationPublic();
    List<Notification> getAllNotification();
    int countUnreadNotification();
    void markAsRead();
}
