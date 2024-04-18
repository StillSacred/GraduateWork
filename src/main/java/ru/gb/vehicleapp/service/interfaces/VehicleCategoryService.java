package ru.gb.vehicleapp.service.interfaces;

import ru.gb.vehicleapp.model.VehicleCategory;
import java.util.Optional;

/**
 * Сервис категорий ТС
 */
public interface VehicleCategoryService {

    /**
     * Создание новой категории ТС
     * @param categoryName - имя катеогрии ТС
     * @return VehicleCategory - сущность категории ТС
     */
    VehicleCategory create(String categoryName);

    /**
     * Поиск категории ТС по имени
     * @param categoryName - имя катеогрии ТС
     * @return VehicleCategory - сущность категории ТС
     */
    Optional<VehicleCategory> findByName(String categoryName);

    /**
     * Обновление категории ТС в репозитории
     * @param category - категория ТС
     */
    VehicleCategory update(VehicleCategory category);
}