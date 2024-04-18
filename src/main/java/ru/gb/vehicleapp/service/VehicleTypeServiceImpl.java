package ru.gb.vehicleapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.vehicleapp.exceptions.ExistingValueException;
import ru.gb.vehicleapp.model.VehicleType;
import ru.gb.vehicleapp.repository.VehicleTypeRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleTypeService;
import ru.gb.vehicleapp.service.response.VehicleTypeResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис типов ТС
 */
@Service
@RequiredArgsConstructor
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private final VehicleTypeRepository typeRepository;

    /**
     * Создание нового типа ТС
     * @param typeName - имя типа ТС
     * @return - VehicleType - сущность типа ТС
     */
    @Override
    public VehicleType create(String typeName) throws ExistingValueException {
        VehicleType type = new VehicleType();
        type.setName(typeName);
        type.setVehicles(new ArrayList<>());
        return typeRepository.save(type);
    }

    /**
     * Пооиск типа ТС по имени
     *
     * @param typeName - имя типа ТС
     * @return - VehicleType - сущность типа ТС
     */
    @Override
    public Optional<VehicleType> findByName(String typeName) {
        return typeRepository.findByNameIgnoreCase(typeName);
    }

    /**
     * Получение всех типов ТС из БД
     * @return List - список типов ТС
     */
    @Override
    public List<VehicleTypeResponse> findAll() {
        List<VehicleType> types = typeRepository.findAll();
        return types.stream().map(this::mapToResponse).toList();
    }

    /**
     * Оновление типа ТС
     * @param type - тип ТС
     */
    @Override
    public VehicleType update(VehicleType type) {
        return typeRepository.save(type);
    }

    /**
     * Маппер VehicleType в VehicleTypeResponse
     * @param type тип ТС
     * @return response-объект с параметрами типа ТС
     */
    private VehicleTypeResponse mapToResponse(VehicleType type) {
        VehicleTypeResponse response = new VehicleTypeResponse();
        response.setTypeName(type.getName());
        return response;
    }
}