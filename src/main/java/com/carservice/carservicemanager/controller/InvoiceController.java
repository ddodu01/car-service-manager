package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.Invoice;
import com.carservice.carservicemanager.service.InvoiceService;
import com.carservice.carservicemanager.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receptionist/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final RepairService repairService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("invoices", invoiceService.getAll());
        return "receptionist/invoices/list";
    }

    @GetMapping("/generate/{repairId}")
    public String generateForm(@PathVariable Long repairId, Model model) {
        model.addAttribute("repair", repairService.getById(repairId)
                .orElseThrow(() -> new RuntimeException("Repair not found")));
        return "receptionist/invoices/generate";
    }

    @PostMapping("/generate/{repairId}")
    public String generate(@PathVariable Long repairId,
                           RedirectAttributes redirectAttributes) {
        Invoice invoice = invoiceService.genereazaFactura(repairId);
        redirectAttributes.addFlashAttribute("success", "Factura generata cu succes!");
        return "redirect:/receptionist/invoices/view/" + invoice.getId();
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        model.addAttribute("invoice", invoice);
        return "receptionist/invoices/view";
    }
}