package uz.carapp.rentcarapp.rest;

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
import uz.carapp.rentcarapp.repository.CarParamRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarParamService;
import uz.carapp.rentcarapp.service.dto.CarParamDTO;
import uz.carapp.rentcarapp.service.dto.CarParamEditDTO;
import uz.carapp.rentcarapp.service.dto.CarParamSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarParam}.
 */
@RestController
@RequestMapping("/api/merchant/car-params")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class CarParamResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarParamResource.class);

    private static final String ENTITY_NAME = "carParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarParamService carParamService;

    private final CarParamRepository carParamRepository;

    public CarParamResource(CarParamService carParamService, CarParamRepository carParamRepository) {
        this.carParamService = carParamService;
        this.carParamRepository = carParamRepository;
    }

    /**
     * {@code POST  /car-params} : Create a new carParam.
     *
     * @param carParamSaveDTO the carParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carParamDTO, or with status {@code 400 (Bad Request)} if the carParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<CarParamDTO> createCarParam(@RequestBody CarParamSaveDTO carParamSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save CarParam : {}", carParamSaveDTO);
        CarParamDTO carParamDTO = carParamService.save(carParamSaveDTO);
        return ResponseEntity.created(new URI("/api/car-params/" + carParamDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carParamDTO.getId().toString()))
            .body(carParamDTO);
    }

    /**
     * {@code PATCH  /car-params/:id} : Partial updates given fields of an existing carParam, field will ignore if it is null
     *
     * @param carParamEditDTO the carParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carParamDTO,
     * or with status {@code 400 (Bad Request)} if the carParamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carParamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<CarParamDTO> partialUpdateCarParam(
        @RequestBody CarParamEditDTO carParamEditDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update CarParam partially : {}", carParamEditDTO);
        if (carParamEditDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carParamRepository.existsById(carParamEditDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarParamDTO> result = carParamService.partialUpdate(carParamEditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carParamEditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /car-params} : get all the carParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carParams in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<List<CarParamDTO>> getAllCarParams(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of CarParams");
        Page<CarParamDTO> page = carParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-params/:id} : get the "id" carParam.
     *
     * @param id the id of the carParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<CarParamDTO> getCarParam(@PathVariable("id") Long id) {
        LOG.info("REST request to get CarParam : {}", id);
        Optional<CarParamDTO> carParamDTO = carParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carParamDTO);
    }

    /**
     * {@code DELETE  /car-params/:id} : delete the "id" carParam.
     *
     * @param id the id of the carParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.OWNER + "\")")
    public ResponseEntity<Void> deleteCarParam(@PathVariable("id") Long id) {
        LOG.info("REST request to delete CarParam : {}", id);
        carParamService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
