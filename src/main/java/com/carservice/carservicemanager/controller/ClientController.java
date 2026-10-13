package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Client;
import com.carservice.carservicemanager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("clients", clientService.findByNameOrSurname(search));
        } else {
            model.addAttribute("clients", clientService.getAll());
        }
        model.addAttribute("search", search);
        return "receptionist/clients/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("client", new Client());
        return "receptionist/clients/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Client client,
                      RedirectAttributes redirectAttributes) {
        if (clientService.existsByUsername(client.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username deja exista!");
            return "redirect:/receptionist/clients/add";
        }
        if (clientService.existsByEmail(client.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email deja exista!");
            return "redirect:/receptionist/clients/add";
        }
        clientService.save(client);
        redirectAttributes.addFlashAttribute("success", "Client adaugat cu succes!");
        return "redirect:/receptionist/clients";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Client client = clientService.getById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        return "receptionist/clients/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Client client,
                       RedirectAttributes redirectAttributes) {
        clientService.update(id, client);
        redirectAttributes.addFlashAttribute("success", "Client actualizat cu succes!");
        return "redirect:/receptionist/clients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        clientService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Client sters cu succes!");
        return "redirect:/receptionist/clients";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Client client = clientService.getById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        return "receptionist/clients/view";
    }
}