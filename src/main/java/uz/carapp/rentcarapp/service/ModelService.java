package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.ModelDTO;
import uz.carapp.rentcarapp.service.dto.ModelEditDTO;
import uz.carapp.rentcarapp.service.dto.ModelSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.Model}.
 */
public interface ModelService {
    /**
     * Save a model.
     *
     * @param modelSaveDTO the entity to save.
     * @return the persisted entity.
     */
    ModelDTO save(ModelSaveDTO modelSaveDTO);

    /**
     * Updates a model.
     *
     * @param modelDTO the entity to update.
     * @return the persisted entity.
     */
    ModelDTO update(ModelDTO modelDTO);

    /**
     * Partially updates a model.
     *
     * @param modelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModelDTO> partialUpdate(ModelEditDTO modelDTO);

    /**
     * Get all the models.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" model.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModelDTO> findOne(Long id);

    /**
     * Delete the "id" model.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
