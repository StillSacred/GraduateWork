package ru.gb.vehicleapp.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.vehicleapp.model.VehicleModel;
import ru.gb.vehicleapp.repository.VehicleBrandRepository;
import ru.gb.vehicleapp.repository.VehicleModelRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleBrandService;
import ru.gb.vehicleapp.service.interfaces.VehicleModelService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VehicleModelServiceTest {

    @Autowired
    private VehicleModelRepository modelRepository;
    @Autowired
    private VehicleBrandRepository brandRepository;
    @Autowired
    private VehicleModelService modelService;
    @Autowired
    private VehicleBrandService brandService;

    @Test
    void createTest() {
        String brandName = "brand";
        String modelName = "model";
        brandService.create(brandName);

        VehicleModel model = modelService.create(brandName, modelName);

        Assertions.assertNotNull(model.getId());
        Assertions.assertEquals(modelName, model.getName());
        Assertions.assertEquals(brandName, model.getBrand().getName());
    }

    @Test
    void findByNameTest() {
        String brandName = "brand";
        String modelName = "model";
        brandService.create(brandName);
        VehicleModel model = modelService.create(brandName, modelName);

        VehicleModel found = modelService.findByName(modelName).orElseThrow();

        Assertions.assertEquals(model.getId(), found.getId());
        Assertions.assertEquals(model.getName(), found.getName());
    }

    @Test
    void updateTest() {
        String brandName = "brand";
        String modelOldName = "model";
        brandService.create(brandName);
        VehicleModel model = modelService.create(brandName, modelOldName);

        model.setName("new name");
        VehicleModel updated = modelService.update(model);

        Assertions.assertEquals(model.getId(), updated.getId());
        Assertions.assertEquals(model.getName(), updated.getName());
        Assertions.assertNotEquals(modelOldName, updated.getName());
    }

    @BeforeEach
    void cleanDB() {
        brandRepository.deleteAll();
        modelRepository.deleteAll();
    }
}