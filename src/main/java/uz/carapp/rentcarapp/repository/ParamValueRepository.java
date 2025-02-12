package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.ParamValue;

/**
 * Spring Data JPA repository for the ParamValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamValueRepository extends JpaRepository<ParamValue, Long> {}
