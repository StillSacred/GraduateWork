package ru.gb.vehicleapp.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.vehicleapp.model.VehicleType;
import ru.gb.vehicleapp.repository.VehicleTypeRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleTypeService;
import ru.gb.vehicleapp.service.response.VehicleTypeResponse;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VehicleTypeServiceTest {

    @Autowired
    private VehicleTypeRepository repository;

    @Autowired
    private VehicleTypeService service;

    @Test
    void createTest() {
        String expectedName = "type";

        VehicleType actual = service.create(expectedName);

        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expectedName, actual.getName());
    }

    @Test
    void findByNameTest() {
        String name = "name";
        VehicleType type = service.create(name);

        VehicleType found = service.findByName(name).orElseThrow();

        Assertions.assertEquals(type.getId(), found.getId());
        Assertions.assertEquals(type.getName(), found.getName());
    }

    @Test
    void findAllTest() {
        service.create("1");
        service.create("2");
        service.create("3");

        List<VehicleTypeResponse> responses = service.findAll();
        Assertions.assertNotNull(responses);
        Assertions.assertEquals(3, responses.size());
    }

    @Test
    void updateTest() {
        String oldName = "old name";
        VehicleType saved = service.create(oldName);
        saved.setName("new name");
        VehicleType updated = service.update(saved);

        Assertions.assertEquals(saved.getId(), updated.getId());
        Assertions.assertEquals(saved.getName(), updated.getName());
        Assertions.assertNotEquals(oldName, updated.getName());
    }

    @BeforeEach
    void cleanDB() {
        repository.deleteAll();
    }
}