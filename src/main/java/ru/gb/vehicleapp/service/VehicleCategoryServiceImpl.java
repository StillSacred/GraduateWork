package ru.gb.vehicleapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.vehicleapp.exceptions.ExistingValueException;
import ru.gb.vehicleapp.model.VehicleCategory;
import ru.gb.vehicleapp.repository.VehicleCategoryRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleCategoryService;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Сервис категорий ТС
 */
@Service
@RequiredArgsConstructor
public class VehicleCategoryServiceImpl implements VehicleCategoryService {

    private final VehicleCategoryRepository categoryRepository;

    /**
     * Создание новой категории ТС
     * @param categoryName - имя катеогрии ТС
     * @return VehicleCategory - сущность категории ТС
     */
    @Override
    public VehicleCategory create(String categoryName) throws ExistingValueException {
        VehicleCategory category = new VehicleCategory();
        category.setName(categoryName);
        category.setVehicles(new ArrayList<>());
        return categoryRepository.save(category);
    }

    /**
     * Поиск категории ТС по имени
     * @param categoryName - имя катеогрии ТС
     * @return VehicleCategory - сущность категории ТС
     */
    @Override
    public Optional<VehicleCategory> findByName(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName);
    }

    /**
     * Обновление категории ТС в репозитории
     * @param category - категория ТС
     */
    @Override
    public VehicleCategory update(VehicleCategory category) {
        return categoryRepository.save(category);
    }
}