package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.repository.ClientRepository;
import com.carservice.carservicemanager.repository.EmployeeRepository;
import com.carservice.carservicemanager.service.AppointmentService;
import com.carservice.carservicemanager.service.ClientService;
import com.carservice.carservicemanager.service.RepairService;
import com.carservice.carservicemanager.service.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ClientService clientService;
    private final RepairService repairService;
    private final SparePartService sparePartService;
    private final AppointmentService appointmentService;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @GetMapping("/manager/dashboard")
    public String managerDashboard(Model model) {
        model.addAttribute("totalClienti", clientService.getAll().size());
        model.addAttribute("totalReparatii", repairService.getAll().size());
        model.addAttribute("reparatiiActive", repairService.findByStatus("In lucru").size());
        model.addAttribute("pieseStocScazut", sparePartService.findByStocScazut().size());
        return "manager/dashboard";
    }

    @GetMapping("/receptionist/dashboard")
    public String receptionistDashboard(Model model) {
        model.addAttribute("totalClienti", clientService.getAll().size());
        model.addAttribute("programari", appointmentService.getAll().size());
        model.addAttribute("reparatiiActive", repairService.findByStatus("In lucru").size());
        return "receptionist/dashboard";
    }

    @GetMapping("/mechanic/dashboard")
    public String mechanicDashboard(Model model, Authentication authentication) {
        var employee = employeeRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("repairs", repairService.findByEmployeeId(employee.getId()));
        model.addAttribute("reparatiiActive", repairService.findByStatus("In lucru").size());
        return "mechanic/dashboard";
    }

    @GetMapping("/client/dashboard")
    public String clientDashboard(Model model, Authentication authentication) {
        var client = clientRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("appointments", appointmentService.findByClientId(client.getId()));
        model.addAttribute("vehicles", client.getVehicles());
        return "client/dashboard";
    }
}