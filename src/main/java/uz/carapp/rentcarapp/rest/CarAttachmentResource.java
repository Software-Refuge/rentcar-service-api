package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.carapp.rentcarapp.repository.CarAttachmentRepository;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarAttachmentService;
import uz.carapp.rentcarapp.service.dto.CarAttachmentSaveDTO;

import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarAttachment}.
 */
@RestController
@RequestMapping("/api/merchant/car-attachments")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class CarAttachmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarAttachmentResource.class);

    private static final String ENTITY_NAME = "carAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarAttachmentService carAttachmentService;

    private final CarAttachmentRepository carAttachmentRepository;

    public CarAttachmentResource(CarAttachmentService carAttachmentService, CarAttachmentRepository carAttachmentRepository) {
        this.carAttachmentService = carAttachmentService;
        this.carAttachmentRepository = carAttachmentRepository;
    }

    /**
     * {@code POST  /car-attachments} : Create a new carAttachment.
     *
     * @param carAttachmentSaveDTO the carAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carAttachmentDTO, or with status {@code 400 (Bad Request)} if the carAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    @Operation(summary = "Attach image to car")
    public ResponseEntity<Void> createCarAttachment(@Valid @RequestBody CarAttachmentSaveDTO carAttachmentSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save CarAttachment : {}", carAttachmentSaveDTO);
        carAttachmentService.save(carAttachmentSaveDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PATCH  /car-attachments/:id} : Partial updates given fields of an existing carAttachment, field will ignore if it is null
     *
     * @param carAttachmentDTO the carAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the carAttachmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carAttachmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
/*    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    @Operation(summary = "Update main status for image")
    public ResponseEntity<CarAttachmentDTO> partialUpdateCarAttachment(
        @RequestBody CarAttachmentDTO carAttachmentDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update CarAttachment partially : {}", carAttachmentDTO);
        if (carAttachmentDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carAttachmentRepository.existsById(id)) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarAttachmentDTO> result = carAttachmentService.partialUpdate(carAttachmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carAttachmentDTO.getId().toString())
        );
    }*/

    /**
     * {@code GET  /car-attachments} : get all the carAttachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carAttachments in body.
     */
/*    @GetMapping("")
    public ResponseEntity<List<CarAttachmentDTO>> getAllCarAttachments(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of CarAttachments");
        Page<CarAttachmentDTO> page = carAttachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/

    /**
     * {@code GET  /car-attachments/:id} : get the "id" carAttachment.
     *
     * @param id the id of the carAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
/*    @GetMapping("/{id}")
    public ResponseEntity<CarAttachmentDTO> getCarAttachment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CarAttachment : {}", id);
        Optional<CarAttachmentDTO> carAttachmentDTO = carAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carAttachmentDTO);
    }*/

    /**
     * {@code DELETE  /car-attachments/:id} : delete the "id" carAttachment.
     *
     * @param id the id of the carAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
/*    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarAttachment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CarAttachment : {}", id);
        carAttachmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/

    @PostMapping("/merchant/set-main-photo")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    @Operation(summary = "Set main photo")
    public ResponseEntity<Void> setMainPhoto(@RequestParam(value = "carId") Long carId,
                                             @RequestParam(value = "attachmentId") Long attachmentId) throws URISyntaxException {
        LOG.info("REST request to set main photo");
        carAttachmentService.setMainPhoto(carId,attachmentId);

        return ResponseEntity.ok().build();
    }

}
