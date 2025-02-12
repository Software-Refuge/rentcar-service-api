package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.Translation;

/**
 * Spring Data JPA repository for the Translation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {}
