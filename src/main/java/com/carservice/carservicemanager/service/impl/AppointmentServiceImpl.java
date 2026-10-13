package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Appointment;
import com.carservice.carservicemanager.repository.AppointmentRepository;
import com.carservice.carservicemanager.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findByClientId(Long clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    @Override
    public List<Appointment> findOreDisponibile(LocalDate date) {
        return appointmentRepository.findByDate(date);
    }
}