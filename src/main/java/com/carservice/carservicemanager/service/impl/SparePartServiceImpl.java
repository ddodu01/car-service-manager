package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.SparePart;
import com.carservice.carservicemanager.repository.SparePartRepository;
import com.carservice.carservicemanager.service.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SparePartServiceImpl implements SparePartService {

    private final SparePartRepository sparePartRepository;

    @Override
    public List<SparePart> getAll() {
        return sparePartRepository.findAll();
    }

    @Override
    public Optional<SparePart> getById(Long id) {
        return sparePartRepository.findById(id);
    }

    @Override
    public SparePart save(SparePart sparePart) {
        return sparePartRepository.save(sparePart);
    }

    @Override
    public SparePart update(Long id, SparePart sparePart) {
        SparePart existing = sparePartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SparePart not found"));
        existing.setPartName(sparePart.getPartName());
        existing.setCode(sparePart.getCode());
        existing.setPrice(sparePart.getPrice());
        existing.setStock(sparePart.getStock());
        return sparePartRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        sparePartRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return sparePartRepository.existsByCode(code);
    }

    @Override
    public List<SparePart> findByStocScazut() {
        return sparePartRepository.findByStockLessThan(5);
    }
}