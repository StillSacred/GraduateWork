package ru.gb.vehicleapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.gb.vehicleapp.api.request.VehicleRequest;
import ru.gb.vehicleapp.service.interfaces.VehicleService;
import ru.gb.vehicleapp.service.interfaces.VehicleTypeService;

@Service
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {

    private final VehicleService vehicleService;
    private final VehicleTypeService typeService;

    @Override
    public void run(String... args) throws Exception {
        if (!typeService.findAll().isEmpty()) {
            return;
        }
        createType("Легковой");
        createType("Грузовой");
        createType("Мотоцикл");

        createVehicle("LADA", "Vesta", "Легковой", "M", "А001АА123", "2020",
                "нет");
        createVehicle("LADA", "2112", "Легковой", "M", "Р123КУ123", "2007",
                "да");
        createVehicle("SKODA", "Rapid", "Легковой", "M", "К123АР123", "2021",
                "нет");
        createVehicle("LADA", "Granta", "Легковой", "M", "А321АВ123", "2024",
                "нет");
        createVehicle("LADA", "Niva", "Легковой", "M", "А364АН123", "2024",
                "нет");
        createVehicle("BMW", "X3", "Легковой", "M", "Е111АЕ123", "2022",
                "нет");
        createVehicle("TOYOTA", "Camry", "Легковой", "M", "Р222ОВ123", "2022",
                "нет");
        createVehicle("AUDI", "Q3", "Легковой", "M", "М321ММ123", "2024",
                "нет");
    }

    private void createType(String typeName) {
        typeService.create(typeName);
    }

    private void createVehicle(String brand, String model, String type, String category, String regNumber,
                               String prodYear, String hasTrailer) {
        VehicleRequest request = new VehicleRequest();
        request.setBrand(brand);
        request.setModel(model);
        request.setType(type);
        request.setCategory(category);
        request.setRegNumber(regNumber);
        request.setProdYear(prodYear);
        request.setHasTrailer(hasTrailer);
        vehicleService.create(request);
    }
}