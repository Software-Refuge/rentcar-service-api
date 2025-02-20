package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.BrandDTO;
import uz.carapp.rentcarapp.service.dto.BrandEditDTO;
import uz.carapp.rentcarapp.service.dto.BrandSaveDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.Brand}.
 */
public interface BrandService {
    /**
     * Save a brand.
     *
     * @param brandSaveDTO the entity to save.
     * @return the persisted entity.
     */
    BrandDTO save(BrandSaveDTO brandSaveDTO);

    /**
     * Updates a brand.
     *
     * @param brandDTO the entity to update.
     * @return the persisted entity.
     */
    BrandDTO update(BrandDTO brandDTO);

    /**
     * Partially updates a brand.
     *
     * @param brandEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BrandDTO> partialUpdate(BrandEditDTO brandEditDTO);

    /**
     * Get all the brands.
     *
     * @return the list of entities.
     */
    Page<BrandDTO> findAll(Pageable pageable);

    /**
     * Get the "id" brand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BrandDTO> findOne(Long id);

    /**
     * Delete the "id" brand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void uploadImage(Long brandId, Long attachmentId);
}
