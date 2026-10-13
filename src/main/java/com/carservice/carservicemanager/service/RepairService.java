package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Repair;
import java.util.List;
import java.util.Optional;

public interface RepairService {
    List<Repair> getAll();
    Optional<Repair> getById(Long id);
    Repair save(Repair repair);
    Repair update(Long id, Repair repair);
    void delete(Long id);
    List<Repair> findByEmployeeId(Long employeeId);
    List<Repair> findByVehicleId(Long vehicleId);
    List<Repair> findByStatus(String status);
    boolean hasActiveRepair(Long vehicleId);
}