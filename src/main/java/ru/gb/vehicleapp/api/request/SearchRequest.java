package ru.gb.vehicleapp.api.request;

import lombok.Data;

/**
 *  request-объект с параметрами поиска ТС
 */
@Data
public class SearchRequest {
    private String brand;
    private String model;
    private String category;
    private String regNumber;
    private String prodYear;
}