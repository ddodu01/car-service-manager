package com.carservice.carservicemanager.repository;

import com.carservice.carservicemanager.model.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByEmployeeId(Long employeeId);
    List<Repair> findByVehicleId(Long vehicleId);
    List<Repair> findByStatus(String status);
    boolean existsByVehicleIdAndStatus(Long vehicleId, String status);
}