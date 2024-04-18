package ru.gb.vehicleapp.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.vehicleapp.model.VehicleBrand;
import ru.gb.vehicleapp.repository.VehicleBrandRepository;
import ru.gb.vehicleapp.service.VehicleBrandServiceImpl;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VehicleBrandServiceImplTest {
    private final String brandName = "name";
    @Mock
    private VehicleBrandRepository brandRepository;

    @InjectMocks
    private VehicleBrandServiceImpl brandService;

    @Test
    void create_shouldCallRepository() {
        VehicleBrand brandForSave = new VehicleBrand();
        brandForSave.setName(brandName);
        brandForSave.setModels(new ArrayList<>());

        Mockito.when(brandRepository.save(Mockito.any(VehicleBrand.class))).thenReturn(brandForSave);
        VehicleBrand actual = brandService.create(brandName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(brandForSave, actual);
        Mockito.verify(brandRepository).save(brandForSave);
    }

    @Test
    void findByName_shouldCallRepository() {
        Optional<VehicleBrand> brand = Optional.of(Mockito.mock(VehicleBrand.class));

        Mockito.when(brandRepository.findByNameIgnoreCase(brandName)).thenReturn(brand);

        Optional<VehicleBrand> actual = brandService.findByName(brandName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(brand, actual);
        Mockito.verify(brandRepository).findByNameIgnoreCase(brandName);
    }

    @Test
    void update_shouldCallRepository() {
        VehicleBrand brandForUpdate = new VehicleBrand();
        brandForUpdate.setId(UUID.randomUUID());
        brandForUpdate.setName(brandName);
        brandForUpdate.setModels(new ArrayList<>());

        Mockito.when(brandRepository.save(Mockito.any(VehicleBrand.class))).thenReturn(brandForUpdate);

        VehicleBrand actual = brandService.update(brandForUpdate);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(brandForUpdate, actual);
        Mockito.verify(brandRepository).save(brandForUpdate);
    }
}