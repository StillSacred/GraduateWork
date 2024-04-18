package ru.gb.vehicleapp.service.response;

import lombok.Data;
import java.util.UUID;

/**
 * response-объект с параметрами ТС
 */
@Data
public class VehicleResponse {
    private UUID id;
    private String brand;
    private String model;
    private String type;
    private String category;
    private String regNumber;
    private int prodYear;
    private boolean hasTrailer;
}