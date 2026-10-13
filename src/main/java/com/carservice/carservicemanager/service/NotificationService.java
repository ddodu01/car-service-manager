package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getAll();
    Notification save(Notification notification);
    void trimitNotificare(Long repairId);
    List<Notification> findByStatus(String status);
}