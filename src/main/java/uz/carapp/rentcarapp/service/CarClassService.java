package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.CarClassDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.CarClass}.
 */
public interface CarClassService {
    /**
     * Save a carClass.
     *
     * @param carClassDTO the entity to save.
     * @return the persisted entity.
     */
    CarClassDTO save(CarClassDTO carClassDTO);

    /**
     * Updates a carClass.
     *
     * @param carClassDTO the entity to update.
     * @return the persisted entity.
     */
    CarClassDTO update(CarClassDTO carClassDTO);

    /**
     * Partially updates a carClass.
     *
     * @param carClassDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarClassDTO> partialUpdate(CarClassDTO carClassDTO);

    /**
     * Get all the carClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarClassDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarClassDTO> findOne(Long id);

    /**
     * Delete the "id" carClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
