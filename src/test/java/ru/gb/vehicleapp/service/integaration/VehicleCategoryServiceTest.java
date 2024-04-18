package ru.gb.vehicleapp.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.vehicleapp.model.VehicleCategory;
import ru.gb.vehicleapp.repository.VehicleCategoryRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleCategoryService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VehicleCategoryServiceTest {

    @Autowired
    private VehicleCategoryRepository repository;
    @Autowired
    private VehicleCategoryService service;

    @Test
    void createTest() {
        String expectedName = "category";

        VehicleCategory actual = service.create(expectedName);

        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expectedName, actual.getName());
    }

    @Test
    void findByNameTest() {
        String categoryName = "name";
        VehicleCategory saved = service.create(categoryName);

        VehicleCategory found = service.findByName(categoryName).orElseThrow();

        Assertions.assertEquals(saved.getId(), found.getId());
        Assertions.assertEquals(saved.getName(), found.getName());
    }

    @Test
    void updateTest() {
        String oldName = "old name";
        VehicleCategory saved = service.create(oldName);
        saved.setName("new name");
        VehicleCategory updated = service.update(saved);

        Assertions.assertEquals(saved.getId(), updated.getId());
        Assertions.assertEquals(saved.getName(), updated.getName());
        Assertions.assertNotEquals(oldName, updated.getName());
    }

    @BeforeEach
    void cleanDB() {
        repository.deleteAll();
    }
}