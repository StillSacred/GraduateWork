package ru.gb.vehicleapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.vehicleapp.api.request.SearchRequest;
import ru.gb.vehicleapp.api.request.VehicleRequest;
import ru.gb.vehicleapp.exceptions.ExistingValueException;
import ru.gb.vehicleapp.model.Vehicle;
import ru.gb.vehicleapp.model.VehicleCategory;
import ru.gb.vehicleapp.model.VehicleModel;
import ru.gb.vehicleapp.model.VehicleType;
import ru.gb.vehicleapp.repository.VehicleRepository;
import ru.gb.vehicleapp.service.interfaces.VehicleCategoryService;
import ru.gb.vehicleapp.service.interfaces.VehicleModelService;
import ru.gb.vehicleapp.service.interfaces.VehicleService;
import ru.gb.vehicleapp.service.interfaces.VehicleTypeService;
import ru.gb.vehicleapp.service.response.VehicleResponse;
import ru.gb.vehicleapp.service.response.VehicleTypeResponse;
import java.util.List;
import java.util.Optional;

/**
 * Сервис ТС
 */
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleModelService modelService;
    private final VehicleTypeService typeService;
    private final VehicleCategoryService categoryService;

    /**
     * Создание нового ТС
     *
     * @param request - запрос с параметрами создаваемого ТС
     * @return - VehicleResponse - response-объект с параметрами созданного ТС
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VehicleResponse create(VehicleRequest request) throws ExistingValueException {
        checkRegNumber(request.getRegNumber());

        Vehicle vehicle = new Vehicle();
        setModel(request.getModel(), request.getBrand(), vehicle);
        vehicle.setBrand(vehicle.getModel().getBrand().getName());
        setType(request.getType(), vehicle);
        setCategory(request.getCategory(), vehicle);
        vehicle.setRegNumber(request.getRegNumber());
        vehicle.setProdYear(Integer.parseInt(request.getProdYear()));
        vehicle.setHasTrailer(request.getHasTrailer().equalsIgnoreCase("да"));
        Vehicle saved = vehicleRepository.save(vehicle);

        return mapToResponse(saved);
    }

    /**
     * Обновление параметров ТС
     *
     * @param request   - запрос с обновляемыми параметрами
     * @param regNumber - гос. номер обновляемого ТС
     * @return - VehicleResponse - response-объект с параметрами созданного ТС
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VehicleResponse update(VehicleRequest request, String regNumber) {
        Vehicle vehicle = vehicleRepository.findByRegNumberIgnoreCase(regNumber).orElseThrow();
        if (!request.getRegNumber().equalsIgnoreCase(vehicle.getRegNumber())) {
            checkRegNumber(request.getRegNumber());
            vehicle.setRegNumber(request.getRegNumber());
        }
        if (!(Integer.parseInt(request.getProdYear()) == vehicle.getProdYear())) {
            vehicle.setProdYear(Integer.parseInt(request.getProdYear()));
        }
        vehicle.setHasTrailer(request.getHasTrailer().equals("да"));
        if (!vehicle.getModel().getName().equalsIgnoreCase(request.getModel())) {
            VehicleModel oldModel = vehicle.getModel();
            oldModel.getVehicles().remove(vehicle);
            modelService.update(oldModel);
            setModel(request.getModel(), request.getBrand(), vehicle);
        }
        if (!vehicle.getBrand().equalsIgnoreCase(vehicle.getModel().getBrand().getName())) {
            vehicle.setBrand(vehicle.getModel().getBrand().getName());
        }
        if (!vehicle.getType().getName().equalsIgnoreCase(request.getType())) {
            VehicleType oldType = vehicle.getType();
            oldType.getVehicles().remove(vehicle);
            typeService.update(oldType);
            setType(request.getType(), vehicle);
        }
        if (!vehicle.getCategory().getName().equals(request.getCategory())) {
            VehicleCategory oldCategory = vehicle.getCategory();
            oldCategory.getVehicles().remove(vehicle);
            categoryService.update(oldCategory);
            setCategory(request.getCategory(), vehicle);
        }
        vehicle = vehicleRepository.save(vehicle);
        return mapToResponse(vehicle);
    }

    /**
     * Получение всех ТС из БД
     *
     * @return List - список response-объектов с параметрами ТС
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponse> findAll(Pageable pageable) {
        Page<Vehicle> page = vehicleRepository.findAll(pageable);
        List<VehicleResponse> responses = page.stream().map(this::mapToResponse).toList();
        return new PageImpl<>(responses, page.getPageable(), page.getTotalElements());
    }

    /**
     * Получение ТС из БД по запросу с параметрами поиска
     *
     * @param request - запрос с параметрами поиска
     * @return List - список response-объектов с параметрами ТС
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponse> findAllByRequest(SearchRequest request, Pageable pageable) {
        String brand = request.getBrand().isEmpty() ? null : request.getBrand();
        String model = request.getModel().isEmpty() ? null : request.getModel();
        String category = request.getCategory().isEmpty() ? null : request.getCategory();
        String regNumber = request.getRegNumber().isEmpty() ? null : request.getRegNumber();
        Integer prodYear = request.getProdYear().isEmpty() ? null : Integer.parseInt(request.getProdYear());
        Page<Vehicle> page = vehicleRepository.findAll(brand, model, category, regNumber, prodYear, pageable);
        List<VehicleResponse> responses = page.stream().map(this::mapToResponse).toList();
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }

    /**
     * Получение ТС из БД по гос. номеру
     *
     * @param regNumber - запрос с параметрами поиска
     * @return List - список response-объектов с параметрами ТС
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleResponse findByRegNumber(String regNumber) {
        Vehicle vehicle = vehicleRepository.findByRegNumberIgnoreCase(regNumber).orElseThrow();
        return mapToResponse(vehicle);
    }

    /**
     * Получение списка типов ТС
     *
     * @return - список типов ТС
     */
    @Override
    public List<VehicleTypeResponse> getTypes() {
        return typeService.findAll();
    }

    @Override
    public void delete(String regNumber) {
        Vehicle vehicle = vehicleRepository.findByRegNumberIgnoreCase(regNumber).orElseThrow();
        vehicleRepository.delete(vehicle);
    }

    /**
     * Маппер Vehicle в VehicleResponse
     *
     * @param vehicle сущость ТС
     * @return response-объект с параметрами ТС
     */
    private VehicleResponse mapToResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setBrand(vehicle.getBrand());
        response.setModel(vehicle.getModel().getName());
        response.setCategory(vehicle.getCategory().getName());
        response.setType(vehicle.getType().getName());
        response.setProdYear(vehicle.getProdYear());
        response.setRegNumber(vehicle.getRegNumber());
        response.setHasTrailer(vehicle.isHasTrailer());
        return response;
    }

    /**
     * Проверка гос. номера ТС на наличие в БД
     *
     * @param regNumber гос. номер ТС
     */
    private void checkRegNumber(String regNumber) {
        if (vehicleRepository.findByRegNumberIgnoreCase(regNumber).isPresent()) {
            throw new ExistingValueException("Транспортное средство с гос. номером: " + regNumber +
                    " уже существует");
        }
    }

    /**
     * Установка модели ТС
     *
     * @param modelName имя модели ТС
     * @param brandName имя марки ТС
     * @param vehicle   сущность ТС
     */
    private void setModel(String modelName, String brandName, Vehicle vehicle) {
        Optional<VehicleModel> optionalModel = modelService.findByName(modelName);
        if (optionalModel.isPresent()) {
            VehicleModel model = optionalModel.get();
            vehicle.setModel(model);
            model.getVehicles().add(vehicle);
        } else {
            VehicleModel model = modelService.create(brandName, modelName);
            vehicle.setModel(model);
            model.getVehicles().add(vehicle);
            modelService.update(model);
        }
    }

    /**
     * Установка типа ТС
     *
     * @param typeName тип ТС
     * @param vehicle  сущность ТС
     */
    private void setType(String typeName, Vehicle vehicle) {
        Optional<VehicleType> optionalType = typeService.findByName(typeName);
        if (optionalType.isPresent()) {
            VehicleType type = optionalType.get();
            vehicle.setType(type);
            type.getVehicles().add(vehicle);
        } else {
            VehicleType type = typeService.create(typeName);
            vehicle.setType(type);
            type.getVehicles().add(vehicle);
            typeService.update(type);
        }
    }

    /**
     * Установка категории ТС
     *
     * @param categoryName категория ТС
     * @param vehicle      сущность ТС
     */
    private void setCategory(String categoryName, Vehicle vehicle) {
        Optional<VehicleCategory> optionalCategory = categoryService.findByName(categoryName);
        if (optionalCategory.isPresent()) {
            VehicleCategory category = optionalCategory.get();
            vehicle.setCategory(category);
            category.getVehicles().add(vehicle);
        } else {
            VehicleCategory category = categoryService.create(categoryName);
            vehicle.setCategory(category);
            category.getVehicles().add(vehicle);
            categoryService.update(category);
        }
    }
}