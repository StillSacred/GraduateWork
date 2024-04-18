package ru.gb.vehicleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.vehicleapp.model.VehicleModel;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий моделей ТС
 */
@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, UUID> {

    /**
     * Поиск молдели ТС по имени с игнорированием регистра
     * @param modelName - имя модели ТС
     * @return - модель ТС
     */
    Optional<VehicleModel> findByNameIgnoreCase(String modelName);
}