package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import uz.carapp.rentcarapp.repository.CarMileageRepository;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarMileageService;
import uz.carapp.rentcarapp.service.dto.CarMileageDTO;
import uz.carapp.rentcarapp.service.dto.CarMileageSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarMileage}.
 */
@RestController
@RequestMapping("/api/merchant/car-mileages")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class CarMileageResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarMileageResource.class);

    private static final String ENTITY_NAME = "carMileage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarMileageService carMileageService;

    private final CarMileageRepository carMileageRepository;

    public CarMileageResource(CarMileageService carMileageService, CarMileageRepository carMileageRepository) {
        this.carMileageService = carMileageService;
        this.carMileageRepository = carMileageRepository;
    }

    /**
     * {@code POST  /car-mileages} : Create a new carMileage.
     *
     * @param carMileageSaveDTO the carMileageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carMileageDTO, or with status {@code 400 (Bad Request)} if the carMileage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    @Operation(summary = "Assign mileage to car")
    public ResponseEntity<CarMileageDTO> createCarMileage(@RequestBody CarMileageSaveDTO carMileageSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save CarMileage : {}", carMileageSaveDTO);

        CarMileageDTO carMileageDTO = carMileageService.save(carMileageSaveDTO);
        return ResponseEntity.created(new URI("/api/car-mileages/" + carMileageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carMileageDTO.getId().toString()))
            .body(carMileageDTO);
    }

    /**
     * {@code PATCH  /car-mileages/:id} : Partial updates given fields of an existing carMileage, field will ignore if it is null
     *
     * @param id the id of the carMileageDTO to save.
     * @param carMileageDTO the carMileageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carMileageDTO,
     * or with status {@code 400 (Bad Request)} if the carMileageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carMileageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carMileageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
/*    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarMileageDTO> partialUpdateCarMileage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CarMileageDTO carMileageDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CarMileage partially : {}, {}", id, carMileageDTO);
        if (carMileageDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carMileageRepository.existsById(id)) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarMileageDTO> result = carMileageService.partialUpdate(carMileageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carMileageDTO.getId().toString())
        );
    }*/

    /**
     * {@code GET  /car-mileages} : get all the carMileages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carMileages in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public List<CarMileageDTO> getAllCarMileages(
            @RequestParam(value = "carId") Long carId,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get all CarMileages");
        return carMileageService.findAll(carId,pageable);
    }

    /**
     * {@code GET  /car-mileages/:id} : get the "id" carMileage.
     *
     * @param id the id of the carMileageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carMileageDTO, or with status {@code 404 (Not Found)}.
     */
/*    @GetMapping("/{id}")
    public ResponseEntity<CarMileageDTO> getCarMileage(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CarMileage : {}", id);
        Optional<CarMileageDTO> carMileageDTO = carMileageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carMileageDTO);
    }*/

    /**
     * {@code DELETE  /car-mileages/:id} : delete the "id" carMileage.
     *
     * @param id the id of the carMileageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
/*    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarMileage(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CarMileage : {}", id);
        carMileageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
