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
import uz.carapp.rentcarapp.repository.CarBodyRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarBodyService;
import uz.carapp.rentcarapp.service.dto.CarBodyDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarBody}.
 */
@RestController
@RequestMapping("/api/v1/car-bodies")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Car Body", description = "The Car Body API.")
public class CarBodyResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarBodyResource.class);

    private static final String ENTITY_NAME = "carBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarBodyService carBodyService;

    private final CarBodyRepository carBodyRepository;

    public CarBodyResource(CarBodyService carBodyService, CarBodyRepository carBodyRepository) {
        this.carBodyService = carBodyService;
        this.carBodyRepository = carBodyRepository;
    }

    /**
     * {@code POST  /car-bodies} : Create a new carBody.
     *
     * @param carBodyDTO the carBodyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carBodyDTO, or with status {@code 400 (Bad Request)} if the carBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create car body")
    public ResponseEntity<CarBodyDTO> createCarBody(@Valid @RequestBody CarBodyDTO carBodyDTO) throws URISyntaxException {
        LOG.info("REST request to save CarBody : {}", carBodyDTO);
        carBodyDTO = carBodyService.save(carBodyDTO);
        return ResponseEntity.created(new URI("/api/car-bodies/" + carBodyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carBodyDTO.getId().toString()))
            .body(carBodyDTO);
    }

    /**
     * {@code PATCH  /car-bodies/:id} : Partial updates given fields of an existing carBody, field will ignore if it is null
     *
     * @param carBodyDTO the carBodyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carBodyDTO,
     * or with status {@code 400 (Bad Request)} if the carBodyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carBodyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carBodyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update car body")
    public ResponseEntity<CarBodyDTO> partialUpdateCarBody(
        @NotNull @RequestBody CarBodyDTO carBodyDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update CarBody partially : {}", carBodyDTO);
        if (carBodyDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carBodyRepository.existsById(carBodyDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarBodyDTO> result = carBodyService.partialUpdate(carBodyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carBodyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /car-bodies} : get all the carBodies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carBodies in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get car body")
    public ResponseEntity<List<CarBodyDTO>> getAllCarBodies(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of CarBodies");
        Page<CarBodyDTO> page = carBodyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-bodies/:id} : get the "id" carBody.
     *
     * @param id the id of the carBodyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carBodyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "List of car body")
    public ResponseEntity<CarBodyDTO> getCarBody(@PathVariable("id") Long id) {
        LOG.info("REST request to get CarBody : {}", id);
        Optional<CarBodyDTO> carBodyDTO = carBodyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carBodyDTO);
    }

    /**
     * {@code DELETE  /car-bodies/:id} : delete the "id" carBody.
     *
     * @param id the id of the carBodyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Delete car body")
    public ResponseEntity<Void> deleteCarBody(@PathVariable("id") Long id) {
        LOG.info("REST request to delete CarBody : {}", id);
        carBodyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
