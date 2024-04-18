package ru.gb.vehicleapp.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * request-объект с параметрами доабвления/изменения ТС
 */
@Data
public class VehicleRequest {
    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String type;

    @NotNull
    private String category;

    @NotNull
    @Pattern(regexp = "[АВЕКМНОРСТУХ]?\\d{3,4}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}$")
    private String regNumber;

    @NotNull
    private String prodYear;

    @NotNull
    private String hasTrailer;
}