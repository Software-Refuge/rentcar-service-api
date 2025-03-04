package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.carapp.rentcarapp.repository.DocAttachmentRepository;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.DocAttachmentService;
import uz.carapp.rentcarapp.service.dto.DocAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.DocAttachmentSaveDTO;


import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.DocAttachment}.
 */
@RestController
@RequestMapping("/api/v1/doc-attachments")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class DocAttachmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocAttachmentResource.class);

    private static final String ENTITY_NAME = "docAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocAttachmentService docAttachmentService;

    private final DocAttachmentRepository docAttachmentRepository;

    public DocAttachmentResource(DocAttachmentService docAttachmentService, DocAttachmentRepository docAttachmentRepository) {
        this.docAttachmentService = docAttachmentService;
        this.docAttachmentRepository = docAttachmentRepository;
    }

    /**
     * {@code POST  /doc-attachments} : Create a new docAttachment.
     *
     * @param documentId the docAttachmentDTO to create.
     * @param attachmentId the docAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docAttachmentDTO, or with status {@code 400 (Bad Request)} if the docAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Assign file to document")
    public ResponseEntity<DocAttachmentDTO> createDocAttachment(@RequestParam(value = "documentId") Long documentId,
                                                                @RequestParam(value = "attachmentId") Long attachmentId) throws URISyntaxException {
        LOG.info("REST request to save DocAttachment : {},{}", documentId,attachmentId);
        DocAttachmentSaveDTO build = DocAttachmentSaveDTO.builder().documentId(documentId).attachmentId(attachmentId).build();
        DocAttachmentDTO docAttachmentDTO = docAttachmentService.save(build);
        return ResponseEntity.ok()
            .body(docAttachmentDTO);
    }

    /**
     * {@code GET  /doc-attachments} : get all the docAttachments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docAttachments in body.
     */
    /*@GetMapping("")
    public List<DocAttachmentDTO> getAllDocAttachments() {
        LOG.debug("REST request to get all DocAttachments");
        return docAttachmentService.findAll();
    }*/

    /**
     * {@code GET  /doc-attachments/:id} : get the "id" docAttachment.
     *
     * @param id the id of the docAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    /*@GetMapping("/{id}")
    public ResponseEntity<DocAttachmentDTO> getDocAttachment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DocAttachment : {}", id);
        Optional<DocAttachmentDTO> docAttachmentDTO = docAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docAttachmentDTO);
    }*/

    /**
     * {@code DELETE  /doc-attachments/:id} : delete the "id" docAttachment.
     *
     * @param id the id of the docAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
   /* @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocAttachment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DocAttachment : {}", id);
        docAttachmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
