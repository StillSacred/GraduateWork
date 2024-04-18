package ru.gb.vehicleapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Модель ТС
 */
@Entity
@Data
@Table(name = "model")
public class VehicleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private VehicleBrand brand;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,
    mappedBy = "model")
    private List<Vehicle> vehicles;
}