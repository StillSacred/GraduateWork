package ru.gb.vehicleapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.vehicleapp.model.Vehicle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий ТС
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, JpaSpecificationExecutor<Vehicle> {

    /**
     * Поиск ТС по гос. номеру с игнорированием регистра
     * @param regNumber - гос. номер ТС
     * @return - ТС
     */
    Optional<Vehicle> findByRegNumberIgnoreCase(String regNumber);

    @Query("select v from Vehicle v where" +
            "(lower(v.brand)= lower(:brand) or :brand is null)" +
            "and (lower(v.model.name) = lower(:model) or :model is null)" +
            "and (lower(v.category.name) = lower(:category) or :category is null)" +
            "and (lower(v.regNumber) = lower(:regNumber) or :regNumber is null)" +
            "and (v.prodYear = :prodYear or :prodYear is null)")
    Page<Vehicle> findAll(@Param("brand") String brand, @Param("model") String model, @Param("category") String category,
                          @Param("regNumber") String regNumber, @Param("prodYear") Integer prodYear, Pageable pageable);
}