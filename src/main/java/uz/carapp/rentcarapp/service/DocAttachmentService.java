package uz.carapp.rentcarapp.service;

import uz.carapp.rentcarapp.service.dto.DocAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.DocAttachmentSaveDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link uz.carapp.rentcarapp.domain.DocAttachment}.
 */
public interface DocAttachmentService {
    /**
     * Save a docAttachment.
     *
     * @param docAttachmentSaveDTO the entity to save.
     * @return the persisted entity.
     */
    DocAttachmentDTO save(DocAttachmentSaveDTO docAttachmentSaveDTO);

    /**
     * Updates a docAttachment.
     *
     * @param docAttachmentDTO the entity to update.
     * @return the persisted entity.
     */
    DocAttachmentDTO update(DocAttachmentDTO docAttachmentDTO);

    /**
     * Partially updates a docAttachment.
     *
     * @param docAttachmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocAttachmentDTO> partialUpdate(DocAttachmentDTO docAttachmentDTO);

    /**
     * Get all the docAttachments.
     *
     * @return the list of entities.
     */
    List<DocAttachmentDTO> findAll();

    /**
     * Get the "id" docAttachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" docAttachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
