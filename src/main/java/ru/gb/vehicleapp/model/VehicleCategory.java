package ru.gb.vehicleapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Категория ТС
 */
@Entity
@Data
@Table(name = "category")
public class VehicleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,
    mappedBy = "category")
    private List<Vehicle> vehicles;
}