package com.carservice.carservicemanager.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(nullable = false)
    private float total;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "repair_id", nullable = false)
    private Repair repair;
}