package ru.gb.vehicleapp.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.vehicleapp.model.VehicleCategory;
import ru.gb.vehicleapp.repository.VehicleCategoryRepository;
import ru.gb.vehicleapp.service.VehicleCategoryServiceImpl;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class VehicleCategoryServiceImplTest {

    private final String categoryName = "name";

    @Mock
    private VehicleCategoryRepository categoryRepository;

    @InjectMocks
    private VehicleCategoryServiceImpl categoryService;

    @Test
    void create_shouldCallRepository() {
        VehicleCategory categoryForSave = new VehicleCategory();
        categoryForSave.setName(categoryName);
        categoryForSave.setVehicles(new ArrayList<>());

        Mockito.when(categoryRepository.save(Mockito.any(VehicleCategory.class))).thenReturn(categoryForSave);

        VehicleCategory actual = categoryService.create(categoryName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(categoryForSave, actual);
        Mockito.verify(categoryRepository).save(categoryForSave);
    }

    @Test
    void findByName_shouldCallRepository() {
        Optional<VehicleCategory> category = Optional.of(Mockito.mock(VehicleCategory.class));

        Mockito.when(categoryRepository.findByNameIgnoreCase(categoryName)).thenReturn(category);

        Optional<VehicleCategory> actual = categoryService.findByName(categoryName);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(category, actual);
        Mockito.verify(categoryRepository).findByNameIgnoreCase(categoryName);
    }

    @Test
    void update_shouldCallRepository() {
        VehicleCategory categoryForUpdate = new VehicleCategory();
        categoryForUpdate.setId(UUID.randomUUID());
        categoryForUpdate.setName(categoryName);
        categoryForUpdate.setVehicles(new ArrayList<>());

        Mockito.when(categoryRepository.save(Mockito.any(VehicleCategory.class))).thenReturn(categoryForUpdate);

        VehicleCategory actual = categoryService.update(categoryForUpdate);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(categoryForUpdate, actual);
        Mockito.verify(categoryRepository).save(categoryForUpdate);
    }
}