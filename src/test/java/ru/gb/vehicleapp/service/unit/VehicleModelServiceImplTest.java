package ru.gb.vehicleapp.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.vehicleapp.model.VehicleBrand;
import ru.gb.vehicleapp.model.VehicleModel;
import ru.gb.vehicleapp.repository.VehicleModelRepository;
import ru.gb.vehicleapp.service.VehicleModelServiceImpl;
import ru.gb.vehicleapp.service.interfaces.VehicleBrandService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VehicleModelServiceImplTest {

    private final String brandName = "name";
    private final String modelName = "name";

    @Mock
    private VehicleBrandService brandService;

    @Mock
    private VehicleModelRepository modelRepository;

    @InjectMocks
    private VehicleModelServiceImpl modelService;

    @Test
    void create_shouldCallServiceAndRepository() {
        VehicleBrand brand = Mockito.mock(VehicleBrand.class);
        VehicleModel modelForSave = new VehicleModel();
        modelForSave.setName(modelName);
        modelForSave.setBrand(brand);
        modelForSave.setVehicles(new ArrayList<>());

        Mockito.when(brandService.findByName(brandName)).thenReturn(Optional.of(brand));
        Mockito.when(modelRepository.save(Mockito.any(VehicleModel.class))).thenReturn(modelForSave);

        VehicleModel actual = modelService.create(brandName, modelName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(modelForSave, actual);
        Mockito.verify(brandService).findByName(brandName);
        Mockito.verify(modelRepository).save(modelForSave);
    }

    @Test
    void create_shouldCallServiceForNewBrandAndRepository() {
        VehicleBrand brand = Mockito.mock(VehicleBrand.class);
        VehicleModel modelForSave = new VehicleModel();
        modelForSave.setName(modelName);
        modelForSave.setBrand(brand);
        modelForSave.setVehicles(new ArrayList<>());

        Mockito.when(brandService.findByName(brandName)).thenReturn(Optional.empty());
        Mockito.when(brandService.create(brandName)).thenReturn(brand);
        Mockito.when(modelRepository.save(Mockito.any(VehicleModel.class))).thenReturn(modelForSave);

        VehicleModel actual = modelService.create(brandName, modelName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(modelForSave, actual);
        Mockito.verify(brandService).findByName(brandName);
        Mockito.verify(brandService).create(brandName);
        Mockito.verify(modelRepository).save(modelForSave);
    }

    @Test
    void findByName_shouldCallRepository() {
        Optional<VehicleModel> model = Optional.of(Mockito.mock(VehicleModel.class));

        Mockito.when(modelRepository.findByNameIgnoreCase(modelName)).thenReturn(model);

        Optional<VehicleModel> actual = modelService.findByName(modelName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(model, actual);
        Mockito.verify(modelRepository).findByNameIgnoreCase(modelName);
    }

    @Test
    void update_shouldCallRepository() {
        VehicleBrand brand = Mockito.mock(VehicleBrand.class);
        VehicleModel modelForUpdate = new VehicleModel();
        modelForUpdate.setId(UUID.randomUUID());
        modelForUpdate.setName(modelName);
        modelForUpdate.setBrand(brand);
        modelForUpdate.setVehicles(new ArrayList<>());

        Mockito.when(modelRepository.save(Mockito.any(VehicleModel.class))).thenReturn(modelForUpdate);

        VehicleModel actual = modelService.update(modelForUpdate);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(modelForUpdate, actual);
        Mockito.verify(modelRepository).save(modelForUpdate);
    }
}