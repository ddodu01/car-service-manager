package com.carservice.carservicemanager.repository;

import com.carservice.carservicemanager.model.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Long> {
    boolean existsByCode(String code);
    List<SparePart> findByStockLessThan(int limit);
}