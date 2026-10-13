package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Invoice;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    List<Invoice> getAll();
    Optional<Invoice> getById(Long id);
    Invoice save(Invoice invoice);
    Invoice genereazaFactura(Long repairId);
}