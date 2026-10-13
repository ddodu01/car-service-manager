package com.carservice.carservicemanager.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "spare_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String partName;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int stock;

    @ManyToMany(mappedBy = "spareParts")
    private List<Repair> repairs;
}