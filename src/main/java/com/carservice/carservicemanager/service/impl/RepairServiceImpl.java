package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Repair;
import com.carservice.carservicemanager.repository.RepairRepository;
import com.carservice.carservicemanager.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService {

    private final RepairRepository repairRepository;

    @Override
    public List<Repair> getAll() {
        return repairRepository.findAll();
    }

    @Override
    public Optional<Repair> getById(Long id) {
        return repairRepository.findById(id);
    }

    @Override
    public Repair save(Repair repair) {
        return repairRepository.save(repair);
    }

    @Override
    public Repair update(Long id, Repair repair) {
        Repair existing = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        existing.setDescription(repair.getDescription());
        existing.setStatus(repair.getStatus());
        existing.setStartDate(repair.getStartDate());
        existing.setEndDate(repair.getEndDate());
        existing.setSpareParts(repair.getSpareParts());
        existing.setManopera(repair.getManopera());
        return repairRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repairRepository.deleteById(id);
    }

    @Override
    public List<Repair> findByEmployeeId(Long employeeId) {
        return repairRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Repair> findByVehicleId(Long vehicleId) {
        return repairRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<Repair> findByStatus(String status) {
        return repairRepository.findByStatus(status);
    }

    @Override
    public boolean hasActiveRepair(Long vehicleId) {
        return repairRepository.existsByVehicleIdAndStatus(vehicleId, "In lucru");
    }
}