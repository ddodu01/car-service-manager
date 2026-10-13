package com.carservice.carservicemanager.controller;

import com.carservice.carservicemanager.model.entity.SparePart;
import com.carservice.carservicemanager.service.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/spareparts")
@RequiredArgsConstructor
public class SparePartController {

    private final SparePartService sparePartService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) Boolean stocScazut) {
        if (stocScazut != null && stocScazut) {
            model.addAttribute("spareParts", sparePartService.findByStocScazut());
            model.addAttribute("stocScazut", true);
        } else {
            model.addAttribute("spareParts", sparePartService.getAll());
            model.addAttribute("stocScazut", false);
        }
        return "manager/spareparts/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("sparePart", new SparePart());
        return "manager/spareparts/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute SparePart sparePart,
                      RedirectAttributes redirectAttributes) {
        if (sparePartService.existsByCode(sparePart.getCode())) {
            redirectAttributes.addFlashAttribute("error", "Cod piesa deja exista!");
            return "redirect:/manager/spareparts/add";
        }
        sparePartService.save(sparePart);
        redirectAttributes.addFlashAttribute("success", "Piesa adaugata cu succes!");
        return "redirect:/manager/spareparts";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SparePart sparePart = sparePartService.getById(id)
                .orElseThrow(() -> new RuntimeException("SparePart not found"));
        model.addAttribute("sparePart", sparePart);
        return "manager/spareparts/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute SparePart sparePart,
                       RedirectAttributes redirectAttributes) {
        sparePartService.update(id, sparePart);
        redirectAttributes.addFlashAttribute("success", "Piesa actualizata cu succes!");
        return "redirect:/manager/spareparts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        sparePartService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Piesa stearsa cu succes!");
        return "redirect:/manager/spareparts";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        SparePart sparePart = sparePartService.getById(id)
                .orElseThrow(() -> new RuntimeException("SparePart not found"));
        model.addAttribute("sparePart", sparePart);
        return "manager/spareparts/view";
    }
}