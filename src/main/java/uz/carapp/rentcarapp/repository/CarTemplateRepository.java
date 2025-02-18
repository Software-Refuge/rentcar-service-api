package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.CarTemplate;

/**
 * Spring Data JPA repository for the CarTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarTemplateRepository extends JpaRepository<CarTemplate, Long> {}
