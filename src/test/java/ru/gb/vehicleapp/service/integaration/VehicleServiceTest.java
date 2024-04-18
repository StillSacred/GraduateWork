package ru.gb.vehicleapp.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.vehicleapp.api.request.VehicleRequest;
import ru.gb.vehicleapp.exceptions.ExistingValueException;
import ru.gb.vehicleapp.repository.*;
import ru.gb.vehicleapp.service.interfaces.VehicleService;
import ru.gb.vehicleapp.service.response.VehicleResponse;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleBrandRepository brandRepository;
    @Autowired
    private VehicleModelRepository modelRepository;
    @Autowired
    private VehicleTypeRepository typeRepository;
    @Autowired
    private VehicleCategoryRepository categoryRepository;

    @Test
    void createWithNewRegNumberTest() {
        VehicleRequest request = new VehicleRequest();
        request.setBrand("brand");
        request.setModel("model");
        request.setType("type");
        request.setCategory("category");
        request.setRegNumber("А001АА111");
        request.setProdYear("2024");
        request.setHasTrailer("да");

        VehicleResponse response = vehicleService.create(request);

        assertNotNull(response.getId());
        assertEquals(request.getBrand(), response.getBrand());
        assertEquals(request.getModel(), response.getModel());
        assertEquals(request.getCategory(), response.getCategory());
        assertEquals(request.getType(), response.getType());
        assertEquals(request.getRegNumber(), response.getRegNumber());
        assertEquals(request.getProdYear(), String.valueOf(response.getProdYear()));
        assertEquals(request.getHasTrailer(), response.isHasTrailer() ? "да" : "нет");
    }

    @Test
    void createButRegNumberExistsInDBTest() {
        VehicleRequest request = new VehicleRequest();
        request.setBrand("brand");
        request.setModel("model");
        request.setType("type");
        request.setCategory("category");
        request.setRegNumber("А001АА111");
        request.setProdYear("2024");
        request.setHasTrailer("да");

        vehicleService.create(request);

        Exception exception = assertThrows(ExistingValueException.class, () -> vehicleService.create(request));
        String message = exception.getMessage();

        assertEquals("Транспортное средство с гос. номером: А001АА111 уже существует", message);
    }

    @Test
    void updateTest() {
        VehicleRequest createRequest = new VehicleRequest();
        createRequest.setBrand("brand");
        createRequest.setModel("model");
        createRequest.setType("type");
        createRequest.setCategory("category");
        createRequest.setRegNumber("А001АА111");
        createRequest.setProdYear("2024");
        createRequest.setHasTrailer("да");
        VehicleResponse response = vehicleService.create(createRequest);
        String oldRegNumber = response.getRegNumber();

        VehicleRequest updateRequest = new VehicleRequest();
        updateRequest.setBrand("new brand");
        updateRequest.setModel("new model");
        updateRequest.setType("new type");
        updateRequest.setCategory("new category");
        updateRequest.setRegNumber("А001АА111");
        updateRequest.setProdYear("2023");
        updateRequest.setHasTrailer("нет");
        VehicleResponse updated = vehicleService.update(updateRequest, oldRegNumber);

        assertEquals(response.getId(), updated.getId());
        assertEquals(updateRequest.getBrand(), updated.getBrand());
        assertEquals(updateRequest.getModel(), updated.getModel());
        assertEquals(updateRequest.getCategory(), updated.getCategory());
        assertEquals(updateRequest.getType(), updated.getType());
        assertEquals(updateRequest.getRegNumber(), updated.getRegNumber());
        assertEquals(updateRequest.getProdYear(), String.valueOf(updated.getProdYear()));
        assertEquals(updateRequest.getHasTrailer(), updated.isHasTrailer() ? "да" : "нет");
    }

    @Test
    void updateButRegNumberExistsInDBTest() {
        VehicleRequest request = new VehicleRequest();
        request.setBrand("brand");
        request.setModel("model");
        request.setType("type");
        request.setCategory("category");
        request.setRegNumber("А001АА111");
        request.setProdYear("2024");
        request.setHasTrailer("да");
        vehicleService.create(request);

        request.setRegNumber("А002АА112");
        vehicleService.create(request);

        VehicleRequest updateRequest = new VehicleRequest();
        updateRequest.setRegNumber("А002АА112");

        Exception exception = assertThrows(ExistingValueException.class, () -> vehicleService.update(updateRequest,
                "А001АА111"));
        String message = exception.getMessage();
        assertEquals("Транспортное средство с гос. номером: А002АА112 уже существует", message);
    }

    @Test
    void findAllTest() {
        VehicleRequest request = new VehicleRequest();
        request.setBrand("brand");
        request.setModel("model");
        request.setType("type");
        request.setCategory("category");
        request.setRegNumber("А001АА111");
        request.setProdYear("2024");
        request.setHasTrailer("да");
        vehicleService.create(request);

        request.setRegNumber("А002АА112");
        vehicleService.create(request);

        Page<VehicleResponse> responses = vehicleService.findAll(PageRequest.of(0, 5));

        assertNotNull(responses);
        assertEquals(2, responses.getNumberOfElements());
    }

    @BeforeEach
    void cleanDB() {
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        typeRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}