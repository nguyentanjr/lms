package com.example.demo.Respository;

import com.example.demo.Model.Notification;
import com.example.demo.Model.NotificationMessage;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.username =:username")
    List<Notification> getAllNotification(String username);
}
