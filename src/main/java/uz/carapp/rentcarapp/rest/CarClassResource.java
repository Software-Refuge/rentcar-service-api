package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import uz.carapp.rentcarapp.repository.CarClassRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarClassService;
import uz.carapp.rentcarapp.service.dto.CarClassDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarClass}.
 */
@RestController
@RequestMapping("/api/v1/car-classes")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Car classes", description = "The Car classes API.")
public class CarClassResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarClassResource.class);

    private static final String ENTITY_NAME = "carClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarClassService carClassService;

    private final CarClassRepository carClassRepository;

    public CarClassResource(CarClassService carClassService, CarClassRepository carClassRepository) {
        this.carClassService = carClassService;
        this.carClassRepository = carClassRepository;
    }

    /**
     * {@code POST  /car-classes} : Create a new carClass.
     *
     * @param carClassDTO the carClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carClassDTO, or with status {@code 400 (Bad Request)} if the carClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create car classes")
    public ResponseEntity<CarClassDTO> createCarClass(@Valid @RequestBody CarClassDTO carClassDTO) throws URISyntaxException {
        LOG.info("REST request to save CarClass : {}", carClassDTO);
        carClassDTO = carClassService.save(carClassDTO);
        return ResponseEntity.created(new URI("/api/car-classes/" + carClassDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carClassDTO.getId().toString()))
            .body(carClassDTO);
    }

    /**
     * {@code PATCH  /car-classes/:id} : Partial updates given fields of an existing carClass, field will ignore if it is null
     *
     * @param carClassDTO the carClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carClassDTO,
     * or with status {@code 400 (Bad Request)} if the carClassDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carClassDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update car classes")
    public ResponseEntity<CarClassDTO> partialUpdateCarClass(
        @NotNull @RequestBody CarClassDTO carClassDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update CarClass partially : {}", carClassDTO);
        if (carClassDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carClassRepository.existsById(carClassDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarClassDTO> result = carClassService.partialUpdate(carClassDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carClassDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /car-classes} : get all the carClasses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carClasses in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get car classes")
    public ResponseEntity<List<CarClassDTO>> getAllCarClasses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of CarClasses");
        Page<CarClassDTO> page = carClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-classes/:id} : get the "id" carClass.
     *
     * @param id the id of the carClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get car classes")
    public ResponseEntity<CarClassDTO> getCarClass(@PathVariable("id") Long id) {
        LOG.info("REST request to get CarClass : {}", id);
        Optional<CarClassDTO> carClassDTO = carClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carClassDTO);
    }

    /**
     * {@code DELETE  /car-classes/:id} : delete the "id" carClass.
     *
     * @param id the id of the carClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "List of car classes")
    public ResponseEntity<Void> deleteCarClass(@PathVariable("id") Long id) {
        LOG.info("REST request to delete CarClass : {}", id);
        carClassService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
