package com.carservice.carservicemanager.repository;

import com.carservice.carservicemanager.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByClientId(Long clientId);
    boolean existsByLicensePlate(String licensePlate);
    boolean existsByVIN(String VIN);
}