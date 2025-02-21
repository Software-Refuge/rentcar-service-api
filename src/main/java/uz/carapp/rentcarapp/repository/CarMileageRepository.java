package uz.carapp.rentcarapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.CarMileage;
import uz.carapp.rentcarapp.domain.enumeration.MileageEnum;

import java.util.Optional;

/**
 * Spring Data JPA repository for the CarMileage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarMileageRepository extends JpaRepository<CarMileage, Long> {
    @Query("SELECT cm.unit FROM CarMileage cm WHERE cm.car.id = :carId")
    Optional<MileageEnum> findMileageTypeByCarId(@Param("carId") Long carId);

    @Query(value = "select cm from CarMileage cm where cm.car.id=:carId")
    Page<CarMileage>findAll(Long carId, Pageable pageable);
}
