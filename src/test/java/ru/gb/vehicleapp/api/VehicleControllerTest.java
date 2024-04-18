package ru.gb.vehicleapp.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ru.gb.vehicleapp.api.request.SearchRequest;
import ru.gb.vehicleapp.api.request.VehicleRequest;
import ru.gb.vehicleapp.service.interfaces.VehicleService;
import ru.gb.vehicleapp.service.response.VehicleResponse;
import ru.gb.vehicleapp.service.response.VehicleTypeResponse;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController controller;

    @Test
    void getMainPaige_shouldCallService() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<VehicleResponse> page = Mockito.mock(Page.class);

        Mockito.when(vehicleService.findAll(pageable)).thenReturn(page);

        String actual = controller.getMainPaige(Mockito.mock(Model.class), 0, 5);

        Assertions.assertNotNull(actual);
        Mockito.verify(vehicleService).findAll(pageable);
    }

    @Test
    void filter_shouldCallService() {
        VehicleResponse response = Mockito.mock(VehicleResponse.class);
        Page<VehicleResponse> page = Mockito.mock(Page.class);

        Mockito.when(vehicleService.findAllByRequest(Mockito.any(SearchRequest.class),
                Mockito.any(Pageable.class))).thenReturn(page);

        String actual = controller.filter(Mockito.mock(Model.class), Mockito.mock(SearchRequest.class), 0, 5);

        Assertions.assertNotNull(actual);
        Mockito.verify(vehicleService).findAllByRequest(Mockito.any(SearchRequest.class), Mockito.any(Pageable.class));
    }

    @Test
    void getCreationPaige_shouldCallService() {
        VehicleTypeResponse response = Mockito.mock(VehicleTypeResponse.class);

        Mockito.when(vehicleService.getTypes()).thenReturn(List.of(response));

        String actual = controller.getCreationPage(Mockito.mock(Model.class));

        Assertions.assertNotNull(actual);
        Mockito.verify(vehicleService).getTypes();
    }

    @Test
    void createVehicle_shouldCallService() {
        String actual = controller.createVehicle(Mockito.mock(VehicleRequest.class));

        Assertions.assertNotNull(actual);
        Mockito.verify(vehicleService).create(Mockito.any(VehicleRequest.class));
    }

    @Test
    void getUpdatePaige_shouldCallService() {
        String regNumber = "regNumber";
        VehicleTypeResponse typeResponse = Mockito.mock(VehicleTypeResponse.class);
        VehicleResponse vehicleResponse = Mockito.mock(VehicleResponse.class);

        Mockito.when(vehicleService.findByRegNumber(regNumber)).thenReturn(vehicleResponse);
        Mockito.when(vehicleService.getTypes()).thenReturn(List.of(typeResponse));

        String actual = controller.getUpdatePage(regNumber, Mockito.mock(Model.class));

        Assertions.assertNotNull(actual);
        Mockito.verify(vehicleService).findByRegNumber(regNumber);
        Mockito.verify(vehicleService).getTypes();
    }

    @Test
    void updateVehicle() {
        String regNumber = "regNumber";
        VehicleRequest request = Mockito.mock(VehicleRequest.class);
        String actual = controller.updateVehicle(regNumber, request);

        Assertions.assertNotNull(actual);
        Mockito.verify(vehicleService).update(request, regNumber);
    }
}