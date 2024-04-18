package ru.gb.vehicleapp.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gb.vehicleapp.api.request.SearchRequest;
import ru.gb.vehicleapp.api.request.VehicleRequest;
import ru.gb.vehicleapp.service.response.VehicleResponse;
import ru.gb.vehicleapp.service.response.VehicleTypeResponse;
import java.util.List;

/**
 * Сервис ТС
 */
public interface VehicleService {

    /**
     * Создание нового ТС
     * @param request - запрос с параметрами создаваемого ТС
     * @return - VehicleResponse - response-объект с параметрами созданного ТС
     */
    VehicleResponse create(VehicleRequest request);

    /**
     * Обновление параметров ТС
     * @param request - запрос с обновляемыми параметрами
     * @param regNumber - гос. номер обновляемого ТС
     * @return - VehicleResponse - response-объект с параметрами созданного ТС
     */
    VehicleResponse update(VehicleRequest request, String regNumber);

    /**
     * Получение всех ТС из БД
     * @return List - список response-объектов с параметрами ТС
     */
    Page<VehicleResponse> findAll(Pageable pageable);

    /**
     * Получение ТС из БД по запросу с параметрами поиска
     * @param request - запрос с параметрами поиска
     * @return List - список response-объектов с параметрами ТС
     */
    Page<VehicleResponse> findAllByRequest(SearchRequest request, Pageable pageable);

    /**
     * Получение ТС из БД по гос. номеру
     * @param regNumber - запрос с параметрами поиска
     * @return List - список response-объектов с параметрами ТС
     */
    VehicleResponse findByRegNumber(String regNumber);

    /**
     * Получение списка типов ТС
     * @return - список типов ТС
     */
    List<VehicleTypeResponse> getTypes();

    /**
     * Удаление ТС из БД
     * @param regNumber гос. номер ТС
     */
    void delete(String regNumber);
}