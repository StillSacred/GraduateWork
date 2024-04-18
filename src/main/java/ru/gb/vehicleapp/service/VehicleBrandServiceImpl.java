package ru.gb.vehicleapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.vehicleapp.exceptions.ExistingValueException;
import ru.gb.vehicleapp.model.VehicleBrand;
import ru.gb.vehicleapp.repository.VehicleBrandRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleBrandService;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Сервис марок ТС
 */
@Service
@RequiredArgsConstructor
public class VehicleBrandServiceImpl implements VehicleBrandService {

    private final VehicleBrandRepository brandRepository;

    /**
     * Создание новой марки
     * @param brandName - имя марки ТС
     * @return VehicleBrand - сущность марки ТС
     */
    @Override
    public VehicleBrand create(String brandName) throws ExistingValueException {
        VehicleBrand brand = new VehicleBrand();
        brand.setName(brandName);
        brand.setModels(new ArrayList<>());
        return brandRepository.save(brand);
    }

    /**
     * Поиск марки по имени
     *
     * @param brandName - имя марки ТС
     * @return VehicleBrand - сущность марки ТС
     */
    @Override
    public Optional<VehicleBrand> findByName(String brandName) {
        return brandRepository.findByNameIgnoreCase(brandName);
    }

    /**
     * Обновление марки в репозитории
     * @param brand - сущность марки ТС
     */
    @Override
    public VehicleBrand update(VehicleBrand brand) {
        return brandRepository.save(brand);
    }
}