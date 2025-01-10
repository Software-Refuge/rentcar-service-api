package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.MerchantBranchDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchEditDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.MerchantBranch}.
 */
public interface MerchantBranchService {
    /**
     * Save a merchantBranch.
     *
     * @param merchantBranchSaveDTO the entity to save.
     * @return the persisted entity.
     */
    MerchantBranchDTO save(MerchantBranchSaveDTO merchantBranchSaveDTO);

    /**
     * Partially updates a merchantBranch.
     *
     * @param merchantBranchEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MerchantBranchDTO> partialUpdate(MerchantBranchEditDTO merchantBranchEditDTO);

    /**
     * Get all the merchantBranches.
     *
     * @param search the for search
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MerchantBranchDTO> findAll(String search, Pageable pageable);

    /**
     * Get the "id" merchantBranch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MerchantBranchDTO> findOne(Long id);

    /**
     * Delete the "id" merchantBranch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
