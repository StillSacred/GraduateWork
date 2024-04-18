package ru.gb.vehicleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.vehicleapp.model.VehicleType;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий типов ТС
 */
@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, UUID> {

    /**
     * Поиск типа ТС по имени с игнорированием регистра
     * @param name - имя типа ТС
     * @return - тип ТС
     */
    Optional<VehicleType> findByNameIgnoreCase(String name);
}