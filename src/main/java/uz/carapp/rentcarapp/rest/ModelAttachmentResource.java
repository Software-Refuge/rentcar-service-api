package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.carapp.rentcarapp.repository.ModelAttachmentRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestAlertException;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.ModelAttachmentService;
import uz.carapp.rentcarapp.service.dto.ModelAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.ModelAttachmentSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.ModelAttachment}.
 */
@RestController
@RequestMapping("/api/v1/model-attachments")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class ModelAttachmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(ModelAttachmentResource.class);

    private static final String ENTITY_NAME = "modelAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelAttachmentService modelAttachmentService;

    private final ModelAttachmentRepository modelAttachmentRepository;

    public ModelAttachmentResource(ModelAttachmentService modelAttachmentService, ModelAttachmentRepository modelAttachmentRepository) {
        this.modelAttachmentService = modelAttachmentService;
        this.modelAttachmentRepository = modelAttachmentRepository;
    }

    /**
     * {@code POST  /model-attachments} : Create a new modelAttachment.
     *
     * @param modelAttachmentSaveDTO the modelAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelAttachmentDTO, or with status {@code 400 (Bad Request)} if the modelAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Attach image to car")
    public ResponseEntity<ModelAttachmentDTO> createModelAttachment(@RequestBody ModelAttachmentSaveDTO modelAttachmentSaveDTO)
        throws URISyntaxException {
        LOG.info("REST request to save ModelAttachment : {}", modelAttachmentSaveDTO);

        ModelAttachmentDTO modelAttachmentDTO = modelAttachmentService.save(modelAttachmentSaveDTO);
        return ResponseEntity.created(new URI("/api/model-attachments/" + modelAttachmentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, modelAttachmentDTO.getId().toString()))
            .body(modelAttachmentDTO);
    }

    /**
     * {@code PATCH  /model-attachments/:id} : Partial updates given fields of an existing modelAttachment, field will ignore if it is null
     *
     * @param id the id of the modelAttachmentDTO to save.
     * @param modelAttachmentDTO the modelAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the modelAttachmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelAttachmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
/*    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModelAttachmentDTO> partialUpdateModelAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModelAttachmentDTO modelAttachmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ModelAttachment partially : {}, {}", id, modelAttachmentDTO);
        if (modelAttachmentDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!modelAttachmentRepository.existsById(id)) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModelAttachmentDTO> result = modelAttachmentService.partialUpdate(modelAttachmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelAttachmentDTO.getId().toString())
        );
    }*/

    /**
     * {@code GET  /model-attachments} : get all the modelAttachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelAttachments in body.
     */
/*    @GetMapping("")
    public ResponseEntity<List<ModelAttachmentDTO>> getAllModelAttachments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ModelAttachments");
        Page<ModelAttachmentDTO> page = modelAttachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/

    /**
     * {@code GET  /model-attachments/:id} : get the "id" modelAttachment.
     *
     * @param id the id of the modelAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
/*    @GetMapping("/{id}")
    public ResponseEntity<ModelAttachmentDTO> getModelAttachment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ModelAttachment : {}", id);
        Optional<ModelAttachmentDTO> modelAttachmentDTO = modelAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelAttachmentDTO);
    }*/

    /**
     * {@code DELETE  /model-attachments/:id} : delete the "id" modelAttachment.
     *
     * @param id the id of the modelAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteModelAttachment(@PathVariable("id") Long id) {
        LOG.info("REST request to delete ModelAttachment : {}", id);
        modelAttachmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/set-main-photo")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Set main photo")
    public ResponseEntity<Void> setMainPhoto(@RequestParam(value = "modelId") Long modelId,
                                             @RequestParam(value = "attachmentId") Long attachmentId) {
        LOG.info("REST request to set main photo by modelId:{} and attachmentId:{}",modelId,attachmentId);
        modelAttachmentService.setMainPhoto(modelId,attachmentId);

        return ResponseEntity.ok().build();
    }
}
