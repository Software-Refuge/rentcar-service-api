package uz.carapp.rentcarapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.carapp.rentcarapp.service.dto.DocumentDTO;
import uz.carapp.rentcarapp.service.dto.DocumentSaveDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.Document}.
 */
public interface DocumentService {
    /**
     * Save a document.
     *
     * @param documentSaveDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentDTO save(DocumentSaveDTO documentSaveDTO);

    /**
     * Updates a document.
     *
     * @param documentDTO the entity to update.
     * @return the persisted entity.
     */
    DocumentDTO update(DocumentDTO documentDTO);

    /**
     * Partially updates a document.
     *
     * @param documentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentDTO> partialUpdate(DocumentDTO documentDTO);

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" document.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentDTO> findOne(Long id);

    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
