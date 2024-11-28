package com.example.demo.Services.Service;

import com.example.demo.Model.NotificationMessage;

import java.util.List;

public interface NotificationService {
    public void sendNotificationToAll(NotificationMessage message);

    public void sendNotificationToUser(String userId, String message);
    public void sendNotificationToUsers(List<String> userIds, NotificationMessage message);
}
