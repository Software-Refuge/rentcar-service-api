package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.CarMileageDTO;
import uz.carapp.rentcarapp.service.dto.CarMileageSaveDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.CarMileage}.
 */
public interface CarMileageService {
    /**
     * Save a carMileage.
     *
     * @param carMileageSaveDTO the entity to save.
     * @return the persisted entity.
     */
    CarMileageDTO save(CarMileageSaveDTO carMileageSaveDTO);

    /**
     * Updates a carMileage.
     *
     * @param carMileageDTO the entity to update.
     * @return the persisted entity.
     */
    CarMileageDTO update(CarMileageDTO carMileageDTO);

    /**
     * Partially updates a carMileage.
     *
     * @param carMileageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarMileageDTO> partialUpdate(CarMileageDTO carMileageDTO);

    /**
     * Get all the carMileages.
     *
     * @return the list of entities.
     */
    List<CarMileageDTO> findAll(Long carId, Pageable pageable);

    /**
     * Get the "id" carMileage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarMileageDTO> findOne(Long id);

    /**
     * Delete the "id" carMileage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
