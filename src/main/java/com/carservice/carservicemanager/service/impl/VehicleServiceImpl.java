package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Vehicle;
import com.carservice.carservicemanager.repository.VehicleRepository;
import com.carservice.carservicemanager.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> getById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Long id, Vehicle vehicle) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        existing.setBrand(vehicle.getBrand());
        existing.setModel(vehicle.getModel());
        existing.setYear(vehicle.getYear());
        existing.setLicensePlate(vehicle.getLicensePlate());
        existing.setVIN(vehicle.getVIN());
        return vehicleRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> findByClientId(Long clientId) {
        return vehicleRepository.findByClientId(clientId);
    }

    @Override
    public boolean existsByLicensePlate(String licensePlate) {
        return vehicleRepository.existsByLicensePlate(licensePlate);
    }
}