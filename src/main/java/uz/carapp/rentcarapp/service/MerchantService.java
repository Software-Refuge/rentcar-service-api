package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.MerchantDTO;
import uz.carapp.rentcarapp.service.dto.MerchantSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.Merchant}.
 */
public interface MerchantService {
    /**
     * Save a merchant.
     *
     * @param merchantSaveDTO the entity to save.
     * @return the persisted entity.
     */
    MerchantDTO save(MerchantSaveDTO merchantSaveDTO);

    /**
     * Partially updates a merchant.
     *
     * @param merchantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MerchantDTO> partialUpdate(MerchantDTO merchantDTO);

    /**
     * Get all the merchants.
     *
     * @param search the for search
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MerchantDTO> findAll(String search, Pageable pageable);

    /**
     * Get the "id" merchant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MerchantDTO> findOne(Long id);

    /**
     * Delete the "id" merchant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
