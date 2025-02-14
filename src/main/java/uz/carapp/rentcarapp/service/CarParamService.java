package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.CarParamDTO;
import uz.carapp.rentcarapp.service.dto.CarParamEditDTO;
import uz.carapp.rentcarapp.service.dto.CarParamSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.CarParam}.
 */
public interface CarParamService {
    /**
     * Save a carParam.
     *
     * @param carParamSaveDTO the entity to save.
     * @return the persisted entity.
     */
    CarParamDTO save(CarParamSaveDTO carParamSaveDTO);

    /**
     * Updates a carParam.
     *
     * @param carParamDTO the entity to update.
     * @return the persisted entity.
     */
    CarParamDTO update(CarParamDTO carParamDTO);

    /**
     * Partially updates a carParam.
     *
     * @param carParamEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarParamDTO> partialUpdate(CarParamEditDTO carParamEditDTO);

    /**
     * Get all the carParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarParamDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarParamDTO> findOne(Long id);

    /**
     * Delete the "id" carParam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
