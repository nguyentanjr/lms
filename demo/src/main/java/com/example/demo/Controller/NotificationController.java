package com.example.demo.Controller;

import com.example.demo.Model.NotificationMessage;
import com.example.demo.Services.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public NotificationMessage sendNotification(NotificationMessage message) {
        return message;
    }

    @MessageMapping("/private-notification")
    public NotificationMessage privateNotification(
            @Payload NotificationMessage message,
            @Header("simpSessionId") String sessionId) {
        notificationService.sendNotificationToUser(message.getRecipientId(), message);
        return message;
    }

    @PostMapping("/api/notifications/broadcast")
    public ResponseEntity<NotificationMessage> sendBroadcastNotification(
            @   RequestBody NotificationMessage message) {
        notificationService.sendNotificationToAll(message);
        return ResponseEntity.ok(message);
    }
}
