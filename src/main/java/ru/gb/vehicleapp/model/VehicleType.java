package ru.gb.vehicleapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Тип ТС
 */
@Data
@Entity
@Table(name = "type")
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,
    mappedBy = "type")
    private List<Vehicle> vehicles;
}