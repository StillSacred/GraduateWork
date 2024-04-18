package ru.gb.vehicleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.vehicleapp.model.VehicleCategory;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий категорий ТС
 */
@Repository
public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, UUID> {

    /**
     * Поиск категории ТС по имени с игнорированием регистра
     * @param categoryName - имя категории ТС
     * @return - категория ТС
     */
    Optional<VehicleCategory> findByNameIgnoreCase(String categoryName);
}