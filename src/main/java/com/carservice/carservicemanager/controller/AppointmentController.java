package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Appointment;
import com.carservice.carservicemanager.model.entity.Client;
import com.carservice.carservicemanager.security.CustomUserDetailsService;
import com.carservice.carservicemanager.service.AppointmentService;
import com.carservice.carservicemanager.service.ClientService;
import com.carservice.carservicemanager.service.VehicleService;
import com.carservice.carservicemanager.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/client/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final VehicleService vehicleService;
    private final ClientRepository clientRepository;

    @GetMapping
    public String list(Model model, Authentication authentication) {
        System.out.println("DEBUG - Username: " + authentication.getName());
        System.out.println("DEBUG - Autoritati: " + authentication.getAuthorities());
        Client client = clientRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("appointments", appointmentService.findByClientId(client.getId()));
        return "client/appointments/list";
    }

    @GetMapping("/add")
    public String addForm(Model model, Authentication authentication) {
        Client client = clientRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("vehicles", vehicleService.findByClientId(client.getId()));
        return "client/appointments/form";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long vehicleId,
                      @RequestParam String date,
                      @RequestParam String time,
                      @RequestParam String description,
                      Authentication authentication,
                      RedirectAttributes redirectAttributes) {
        Client client = clientRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setDate(java.time.LocalDate.parse(date));
        appointment.setTime(java.time.LocalTime.parse(time));
        appointment.setDescription(description);
        appointment.setStatus("In asteptare");
        appointmentService.save(appointment);
        redirectAttributes.addFlashAttribute("success", "Programare salvata cu succes!");
        return "redirect:/client/appointments";
    }

    @GetMapping("/available")
    public String getOreDisponibile(@RequestParam String date, Model model) {
        LocalDate localDate = LocalDate.parse(date);
        model.addAttribute("appointments", appointmentService.findOreDisponibile(localDate));
        return "client/appointments/available";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        appointmentService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Programare anulata cu succes!");
        return "redirect:/client/appointments";
    }

}