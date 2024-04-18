package ru.gb.vehicleapp.service.interfaces;

import ru.gb.vehicleapp.model.VehicleBrand;
import java.util.Optional;

/**
 * Сервис марок ТС
 */
public interface VehicleBrandService {
    /**
     * Создание новой марки
     * @param brandName - имя марки ТС
     * @return VehicleBrand - сущность марки ТС
     */
    VehicleBrand create(String brandName);

    /**
     * Поиск марки по имени
     * @param brandName - имя марки ТС
     * @return VehicleBrand - сущность марки ТС
     */
    Optional<VehicleBrand> findByName(String brandName);

    /**
     * Обновление марки в репозитории
     * @param brand - сущность марки ТС
     */
    VehicleBrand update(VehicleBrand brand);
}