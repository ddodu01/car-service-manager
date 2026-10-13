package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Notification;
import com.carservice.carservicemanager.model.entity.Repair;
import com.carservice.carservicemanager.repository.NotificationRepository;
import com.carservice.carservicemanager.repository.RepairRepository;
import com.carservice.carservicemanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RepairRepository repairRepository;

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void trimitNotificare(Long repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        Notification notification = new Notification();
        notification.setType("EMAIL");
        notification.setMessage("Reparatia masinii dvs. a fost finalizata.");
        notification.setDate(LocalDate.now());
        notification.setStatus("Trimisa");
        notification.setRepair(repair);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findByStatus(String status) {
        return notificationRepository.findByStatus(status);
    }
}