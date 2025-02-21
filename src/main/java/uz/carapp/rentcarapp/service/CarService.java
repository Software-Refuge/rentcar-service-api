package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.domain.Model;
import uz.carapp.rentcarapp.service.dto.CarDTO;
import uz.carapp.rentcarapp.service.dto.CarEditDTO;
import uz.carapp.rentcarapp.service.dto.CarSaveDTO;
import uz.carapp.rentcarapp.service.dto.ModelDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.Car}.
 */
public interface CarService {
    /**
     * Save a car.
     *
     * @param carSaveDTO the entity to save.
     * @return the persisted entity.
     */
    CarDTO save(CarSaveDTO carSaveDTO);

    /**
     * Updates a car.
     *
     * @param carDTO the entity to update.
     * @return the persisted entity.
     */
    CarDTO update(CarDTO carDTO);

    /**
     * Partially updates a car.
     *
     * @param carEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarDTO> partialUpdate(CarEditDTO carEditDTO);

    /**
     * Get all the cars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarDTO> findAll(Pageable pageable, String lang);

    /**
     * Get the "id" car.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarDTO> findOne(Long id, String lang);

    /**
     * Delete the "id" car.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
