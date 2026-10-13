package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Appointment;
import com.carservice.carservicemanager.model.entity.Repair;
import com.carservice.carservicemanager.service.AppointmentService;
import com.carservice.carservicemanager.service.EmployeeService;
import com.carservice.carservicemanager.service.RepairService;
import com.carservice.carservicemanager.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/receptionist/appointments")
@RequiredArgsConstructor
public class ReceptionistAppointmentController {

    private final AppointmentService appointmentService;
    private final RepairService repairService;
    private final EmployeeService employeeService;
    private final VehicleService vehicleService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("appointments", appointmentService.getAll());
        return "receptionist/appointments/list";
    }

    @GetMapping("/create-repair/{appointmentId}")
    public String createRepairForm(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = appointmentService.getById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        model.addAttribute("appointment", appointment);
        model.addAttribute("repair", new Repair());
        model.addAttribute("mechanics", employeeService.getAll().stream()
                .filter(e -> e.getRole().equals("MECANIC"))
                .toList());
        model.addAttribute("vehicles", vehicleService.findByClientId(appointment.getClient().getId()));
        return "receptionist/appointments/create-repair";
    }


    @PostMapping("/create-repair/{appointmentId}")
    @Transactional
    public String createRepair(@PathVariable Long appointmentId,
                               @RequestParam Long vehicleId,
                               @RequestParam Long employeeId,
                               @RequestParam String description,
                               RedirectAttributes redirectAttributes) {
        Appointment appointment = appointmentService.getById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Repair repair = new Repair();
        repair.setDescription(description);
        repair.setStartDate(LocalDate.now());
        repair.setStatus("In asteptare");

        vehicleService.getById(vehicleId).ifPresent(repair::setVehicle);
        employeeService.getById(employeeId).ifPresent(repair::setEmployee);

        Repair savedRepair = repairService.save(repair);

        appointment.setStatus("Confirmata");
        appointment.setRepair(savedRepair);

        appointmentService.save(appointment);

        redirectAttributes.addFlashAttribute("success", "Reparatie creata si asignata mecanicului!");
        return "redirect:/receptionist/appointments";
    }
}