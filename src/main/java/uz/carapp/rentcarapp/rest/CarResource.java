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
import uz.carapp.rentcarapp.repository.CarRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarService;
import uz.carapp.rentcarapp.service.dto.CarDTO;
import uz.carapp.rentcarapp.service.dto.CarEditDTO;
import uz.carapp.rentcarapp.service.dto.CarSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Car}.
 */
@RestController
@RequestMapping("/api/merchant/cars")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class CarResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarResource.class);

    private static final String ENTITY_NAME = "car";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarService carService;

    private final CarRepository carRepository;

    public CarResource(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    /**
     * {@code POST  /cars} : Create a new car.
     *
     * @param carSaveDTO the carDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carDTO, or with status {@code 400 (Bad Request)} if the car has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    @Operation(summary = "Create new a car")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarSaveDTO carSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save Car : {}", carSaveDTO);
        CarDTO carDTO = carService.save(carSaveDTO);
        return ResponseEntity.created(new URI("/api/cars/" + carDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carDTO.getId().toString()))
            .body(carDTO);
    }

    /**
     * {@code PATCH  /cars/:id} : Partial updates given fields of an existing car, field will ignore if it is null
     *
     * @param carEditDTO the carDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carDTO,
     * or with status {@code 400 (Bad Request)} if the carDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<CarDTO> partialUpdateCar(@RequestBody CarEditDTO carEditDTO)
        throws URISyntaxException {
        LOG.info("REST request to partial update Car partially : {}", carEditDTO);
        if (carEditDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carRepository.existsById(carEditDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarDTO> result = carService.partialUpdate(carEditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carEditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cars} : get all the cars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<List<CarDTO>> getAllCars(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of Cars");
        Page<CarDTO> page = carService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cars/:id} : get the "id" car.
     *
     * @param id the id of the carDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<CarDTO> getCar(@PathVariable("id") Long id) {
        LOG.info("REST request to get Car : {}", id);
        Optional<CarDTO> carDTO = carService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carDTO);
    }

    /**
     * {@code DELETE  /cars/:id} : delete the "id" car.
     *
     * @param id the id of the carDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Car : {}", id);
        carService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
