package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.CarTemplateDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateEditDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.CarTemplate}.
 */
public interface CarTemplateService {
    /**
     * Save a carTemplate.
     *
     * @param carTemplateSaveDTO the entity to save.
     * @return the persisted entity.
     */
    CarTemplateDTO save(CarTemplateSaveDTO carTemplateSaveDTO);

    /**
     * Updates a carTemplate.
     *
     * @param carTemplateDTO the entity to update.
     * @return the persisted entity.
     */
    CarTemplateDTO update(CarTemplateDTO carTemplateDTO);

    /**
     * Partially updates a carTemplate.
     *
     * @param carTemplateEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarTemplateDTO> partialUpdate(CarTemplateEditDTO carTemplateEditDTO);

    /**
     * Get all the carTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarTemplateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" carTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
