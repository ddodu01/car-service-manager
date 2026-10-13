package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Vehicle;
import com.carservice.carservicemanager.service.ClientService;
import com.carservice.carservicemanager.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final ClientService clientService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) Long clientId) {
        if (clientId != null) {
            model.addAttribute("vehicles", vehicleService.findByClientId(clientId));
        } else {
            model.addAttribute("vehicles", vehicleService.getAll());
        }
        model.addAttribute("clients", clientService.getAll());
        return "receptionist/vehicles/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("clients", clientService.getAll());
        return "receptionist/vehicles/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Vehicle vehicle,
                      RedirectAttributes redirectAttributes) {
        if (vehicleService.existsByLicensePlate(vehicle.getLicensePlate())) {
            redirectAttributes.addFlashAttribute("error", "Numar inmatriculare deja exista!");
            return "redirect:/receptionist/vehicles/add";
        }
        vehicleService.save(vehicle);
        redirectAttributes.addFlashAttribute("success", "Vehicul adaugat cu succes!");
        return "redirect:/receptionist/vehicles";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("clients", clientService.getAll());
        return "receptionist/vehicles/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Vehicle vehicle,
                       RedirectAttributes redirectAttributes) {
        vehicleService.update(id, vehicle);
        redirectAttributes.addFlashAttribute("success", "Vehicul actualizat cu succes!");
        return "redirect:/receptionist/vehicles";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        if (vehicleService.getById(id).isPresent()) {
            vehicleService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Vehicul sters cu succes!");
        }
        return "redirect:/receptionist/vehicles";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        model.addAttribute("vehicle", vehicle);
        return "receptionist/vehicles/view";
    }
}