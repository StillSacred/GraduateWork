package ru.gb.vehicleapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Марка ТС
 */
@Data
@Entity
@Table(name = "brand")
public class VehicleBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,
            mappedBy = "brand")
    private List<VehicleModel> models;
}