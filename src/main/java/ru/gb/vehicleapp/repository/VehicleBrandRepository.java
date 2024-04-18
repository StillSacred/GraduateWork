package ru.gb.vehicleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.vehicleapp.model.VehicleBrand;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий марок ТС
 */
@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, UUID> {
    /**
     * Поиск марки ТС по имени с игнорированием регистра
     * @param brandName - имя марки ТС
     * @return - марка ТС
     */
    Optional<VehicleBrand> findByNameIgnoreCase(String brandName);
}