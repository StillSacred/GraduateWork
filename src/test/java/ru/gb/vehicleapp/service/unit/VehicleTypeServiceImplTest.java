package ru.gb.vehicleapp.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.vehicleapp.model.VehicleType;
import ru.gb.vehicleapp.repository.VehicleTypeRepository;
import ru.gb.vehicleapp.service.VehicleTypeServiceImpl;
import ru.gb.vehicleapp.service.response.VehicleTypeResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VehicleTypeServiceImplTest {

    private final String typeName = "name";

    @Mock
    private VehicleTypeRepository typeRepository;

    @InjectMocks
    private VehicleTypeServiceImpl typeService;

    @Test
    void create_shouldCallRepository() {
        VehicleType typeForSave = new VehicleType();
        typeForSave.setName(typeName);
        typeForSave.setVehicles(new ArrayList<>());

        Mockito.when(typeRepository.save(Mockito.any(VehicleType.class))).thenReturn(typeForSave);

        VehicleType actual = typeService.create(typeName);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(typeForSave, actual);
        Mockito.verify(typeRepository).save(typeForSave);
    }

    @Test
    void findByName_shouldCallRepository() {
        Optional<VehicleType> type = Optional.of(Mockito.mock(VehicleType.class));

        Mockito.when(typeRepository.findByNameIgnoreCase(typeName)).thenReturn(type);

        Optional<VehicleType> actual = typeService.findByName(typeName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(type, actual);
        Mockito.verify(typeRepository).findByNameIgnoreCase(typeName);
    }

    @Test
    void findAll_shouldCallRepository() {
        VehicleType type1 = Mockito.mock(VehicleType.class);
        VehicleType type2 = Mockito.mock(VehicleType.class);

        Mockito.when(typeRepository.findAll()).thenReturn(List.of(type1, type2));

        List<VehicleTypeResponse> actual = typeService.findAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Mockito.verify(typeRepository).findAll();
    }

    @Test
    void update_shouldCallRepository() {
        VehicleType typeForUpdate = new VehicleType();
        typeForUpdate.setId(UUID.randomUUID());
        typeForUpdate.setName(typeName);
        typeForUpdate.setVehicles(new ArrayList<>());

        Mockito.when(typeRepository.save(Mockito.any(VehicleType.class))).thenReturn(typeForUpdate);

        VehicleType actual = typeService.update(typeForUpdate);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(typeForUpdate, actual);
        Mockito.verify(typeRepository).save(typeForUpdate);
    }
}