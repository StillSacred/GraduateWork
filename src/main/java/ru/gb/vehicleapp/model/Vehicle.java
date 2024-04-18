package ru.gb.vehicleapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * Сущность ТС
 */
@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn
    private VehicleModel model;

    @ManyToOne
    @JoinColumn
    private VehicleType type;

    @ManyToOne
    @JoinColumn
    private VehicleCategory category;

    @Column(name = "brand")
    private String brand;

    // Регистрационный номер
    @Column(name = "reg_number")
    private String regNumber;

    // Год выпуска
    @Column(name = "prod_year")
    private int prodYear;

    // Наличие прицепа
    @Column(name = "has_trailer")
    private boolean hasTrailer;
}