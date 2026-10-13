package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> getAll();
    Optional<Vehicle> getById(Long id);
    Vehicle save(Vehicle vehicle);
    Vehicle update(Long id, Vehicle vehicle);
    void delete(Long id);
    List<Vehicle> findByClientId(Long clientId);
    boolean existsByLicensePlate(String licensePlate);
}