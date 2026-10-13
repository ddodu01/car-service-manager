package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Invoice;
import com.carservice.carservicemanager.model.entity.Repair;
import com.carservice.carservicemanager.repository.InvoiceRepository;
import com.carservice.carservicemanager.repository.RepairRepository;
import com.carservice.carservicemanager.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final RepairRepository repairRepository;

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice genereazaFactura(Long repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(UUID.randomUUID().toString());
        invoice.setDate(LocalDate.now());
        invoice.setStatus("Emisa");
        invoice.setRepair(repair);
        float total = repair.getSpareParts().stream()
                .map(sp -> sp.getPrice())
                .reduce(0f, Float::sum);
        total += repair.getManopera();
        invoice.setTotal(total);
        return invoiceRepository.save(invoice);
    }
}