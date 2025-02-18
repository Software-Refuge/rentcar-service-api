package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.ParamValueDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueEditDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.ParamValue}.
 */
public interface ParamValueService {
    /**
     * Save a paramValue.
     *
     * @param paramValueSaveDTO the entity to save.
     * @return the persisted entity.
     */
    ParamValueDTO save(ParamValueSaveDTO paramValueSaveDTO);

    /**
     * Updates a paramValue.
     *
     * @param paramValueDTO the entity to update.
     * @return the persisted entity.
     */
    ParamValueDTO update(ParamValueDTO paramValueDTO);

    /**
     * Partially updates a paramValue.
     *
     * @param paramValueEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParamValueDTO> partialUpdate(ParamValueEditDTO paramValueEditDTO);

    /**
     * Get all the paramValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamValueDTO> findAll(Pageable pageable, Long paramId);

    /**
     * Get the "id" paramValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParamValueDTO> findOne(Long id);

    /**
     * Delete the "id" paramValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
