package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.Translation;
import uz.carapp.rentcarapp.domain.enumeration.LanguageEnum;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Translation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    @Query("SELECT t.name FROM Translation t WHERE t.entityType = :entityType AND t.entityId = :entityId AND t.lang = :lang")
    Optional<String> findTranslation(@Param("entityType") String entityType,
                                     @Param("entityId") Long entityId,
                                     @Param("lang") LanguageEnum lang);

    @Query("SELECT t.name FROM Translation t WHERE t.entityType = :entityType AND t.name = :name AND t.lang = :lang")
    Optional<String> findTranslationByFieldName(@Param("entityType") String entityType,
                                     @Param("fieldName") String name,
                                     @Param("lang") LanguageEnum lang);

}
