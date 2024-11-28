package com.example.demo.Services.ServiceImpls;

import com.example.demo.Model.NotificationMessage;
import com.example.demo.Services.Service.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private SimpMessagingTemplate simpMessagingTemplate;
    public void sendNotificationToAll(NotificationMessage message) {
        simpMessagingTemplate.convertAndSend("/topic/notifications", message);
    }

    public void sendNotificationToUser(String userId, String message) {
        NotificationMessage notification    = NotificationMessage.builder()
                .userId(userId)
                .content(message)
                .timestamp(LocalDateTime.now())
                .build();
        simpMessagingTemplate.convertAndSendToUser(
                userId,
                "/queue/private-notifications",
                message
        );
    }

}
