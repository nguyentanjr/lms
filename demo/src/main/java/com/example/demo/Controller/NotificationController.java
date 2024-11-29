package com.example.demo.Controller;

import com.example.demo.Model.Notification;
import com.example.demo.Services.Service.NotificationService;
import com.example.demo.Services.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @GetMapping("/notification")
    public String notification() {
        notificationService.markAsRead();
        System.out.println(222);
        return "notification";
    }
    @GetMapping("/get-all-notification-current-user")
    public ResponseEntity<List<Notification>> getAllNotificationPrivate() {
        return ResponseEntity.ok(notificationService.getAllNotificationPrivate());
    }

    @GetMapping("/get-all-notification-public")
    public ResponseEntity<List<Notification>> getAllNotificationPublic() {
        return ResponseEntity.ok(notificationService.getAllNotificationPublic());
    }
    @GetMapping("/count-unread-notification")
    public ResponseEntity<Integer> countUnreadNotification() {
        return ResponseEntity.ok(notificationService.countUnreadNotification());
    }




}
