package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Employee;
import com.carservice.carservicemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "manager/employees/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "manager/employees/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Employee employee,
                      RedirectAttributes redirectAttributes) {
        if (employeeService.existsByUsername(employee.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username deja exista!");
            return "redirect:/manager/employees/add";
        }
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("success", "Angajat adaugat cu succes!");
        return "redirect:/manager/employees";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", employee);
        return "manager/employees/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Employee employee,
                       RedirectAttributes redirectAttributes) {
        employeeService.update(id, employee);
        redirectAttributes.addFlashAttribute("success", "Angajat actualizat cu succes!");
        return "redirect:/manager/employees";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        employeeService.deactivate(id);
        redirectAttributes.addFlashAttribute("success", "Angajat dezactivat cu succes!");
        return "redirect:/manager/employees";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", employee);
        return "manager/employees/view";
    }

    @GetMapping("/reset-password/{id}")
    public String resetPasswordForm(@PathVariable Long id, Model model) {
        model.addAttribute("employeeId", id);
        return "manager/employees/reset-password";
    }

    @PostMapping("/reset-password/{id}")
    public String resetPassword(@PathVariable Long id,
                                @RequestParam String newPassword,
                                RedirectAttributes redirectAttributes) {
        employeeService.resetPassword(id, newPassword);
        redirectAttributes.addFlashAttribute("success", "Parola resetata cu succes!");
        return "redirect:/manager/employees";
    }
}