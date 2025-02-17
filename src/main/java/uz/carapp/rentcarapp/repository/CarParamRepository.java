package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.CarParam;

import java.util.List;

/**
 * Spring Data JPA repository for the CarParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarParamRepository extends JpaRepository<CarParam, Long> {
    @Query("SELECT cp FROM CarParam cp WHERE cp.car.id IN :carIds")
    List<CarParam> findByCarIds(@Param("carIds") List<Long> carIds);
}
