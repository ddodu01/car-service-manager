package com.carservice.carservicemanager.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "repairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToMany
    @JoinTable(
            name = "repair_spareparts",
            joinColumns = @JoinColumn(name = "repair_id"),
            inverseJoinColumns = @JoinColumn(name = "sparepart_id")
    )
    private List<SparePart> spareParts;

    @OneToOne(mappedBy = "repair", cascade = CascadeType.ALL)
    private Invoice invoice;

    @OneToOne(mappedBy = "repair", cascade = CascadeType.ALL)
    private Notification notification;

    @OneToOne(mappedBy = "repair")
    private Appointment appointment;

    @Column(nullable = false)
    private Float manopera = 0f;
}