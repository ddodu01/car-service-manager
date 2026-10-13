package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Appointment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> getAll();
    Optional<Appointment> getById(Long id);
    Appointment save(Appointment appointment);
    void delete(Long id);
    List<Appointment> findByClientId(Long clientId);
    List<Appointment> findOreDisponibile(LocalDate date);
}