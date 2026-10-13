package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Employee;
import com.carservice.carservicemanager.model.entity.Repair;
import com.carservice.carservicemanager.repository.EmployeeRepository;
import com.carservice.carservicemanager.service.NotificationService;
import com.carservice.carservicemanager.service.RepairService;
import com.carservice.carservicemanager.service.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mechanic/repairs")
@RequiredArgsConstructor
public class RepairController {

    private final RepairService repairService;
    private final SparePartService sparePartService;
    private final NotificationService notificationService;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public String list(Model model, Authentication authentication) {
        Employee employee = employeeRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("repairs", repairService.findByEmployeeId(employee.getId()));
        return "mechanic/repairs/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Repair repair = repairService.getById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        model.addAttribute("repair", repair);
        model.addAttribute("spareParts", sparePartService.getAll());
        return "mechanic/repairs/view";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        Repair repair = repairService.getById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        repair.setStatus(status);
        repairService.update(id, repair);

        if (status.equals("Finalizata")) {
            notificationService.trimitNotificare(id);
        }

        redirectAttributes.addFlashAttribute("success", "Status actualizat cu succes!");
        return "redirect:/mechanic/repairs/view/" + id;
    }

    @PostMapping("/update-details/{id}")
    public String updateDetails(@PathVariable Long id,
                                @RequestParam float manopera,
                                RedirectAttributes redirectAttributes) {
        Repair repair = repairService.getById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        repair.setManopera(manopera);
        repairService.update(id, repair);
        redirectAttributes.addFlashAttribute("success", "Detalii actualizate cu succes!");
        return "redirect:/mechanic/repairs/view/" + id;
    }
}