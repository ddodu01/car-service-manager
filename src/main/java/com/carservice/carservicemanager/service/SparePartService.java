package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.SparePart;
import java.util.List;
import java.util.Optional;

public interface SparePartService {
    List<SparePart> getAll();
    Optional<SparePart> getById(Long id);
    SparePart save(SparePart sparePart);
    SparePart update(Long id, SparePart sparePart);
    void delete(Long id);
    boolean existsByCode(String code);
    List<SparePart> findByStocScazut();
}