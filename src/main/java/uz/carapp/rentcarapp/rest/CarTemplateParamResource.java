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
import uz.carapp.rentcarapp.repository.CarTemplateParamRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarTemplateParamService;
import uz.carapp.rentcarapp.service.dto.CarTemplateParamDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateParamSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarTemplateParam}.
 */
@RestController
@RequestMapping("/api/v1/car-template-params")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class CarTemplateParamResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarTemplateParamResource.class);

    private static final String ENTITY_NAME = "carTemplateParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarTemplateParamService carTemplateParamService;

    private final CarTemplateParamRepository carTemplateParamRepository;

    public CarTemplateParamResource(
        CarTemplateParamService carTemplateParamService,
        CarTemplateParamRepository carTemplateParamRepository
    ) {
        this.carTemplateParamService = carTemplateParamService;
        this.carTemplateParamRepository = carTemplateParamRepository;
    }

    /**
     * {@code POST  /car-template-params} : Create a new carTemplateParam.
     *
     * @param carTemplateParamSaveDTO the carTemplateParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carTemplateParamDTO, or with status {@code 400 (Bad Request)} if the carTemplateParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CarTemplateParamDTO> createCarTemplateParam(@RequestBody CarTemplateParamSaveDTO carTemplateParamSaveDTO)
        throws URISyntaxException {
        LOG.info("REST request to save CarTemplateParam : {}", carTemplateParamSaveDTO);
        CarTemplateParamDTO carTemplateParamDTO = carTemplateParamService.save(carTemplateParamSaveDTO);
        return ResponseEntity.created(new URI("/api/car-template-params/" + carTemplateParamDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carTemplateParamDTO.getId().toString()))
            .body(carTemplateParamDTO);
    }

    /**
     * {@code PATCH  /car-template-params/:id} : Partial updates given fields of an existing carTemplateParam, field will ignore if it is null
     *
     * @param carTemplateParamDTO the carTemplateParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carTemplateParamDTO,
     * or with status {@code 400 (Bad Request)} if the carTemplateParamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carTemplateParamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carTemplateParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
/*    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CarTemplateParamDTO> partialUpdateCarTemplateParam(
        @RequestBody CarTemplateParamDTO carTemplateParamDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update CarTemplateParam partially :  {}", carTemplateParamDTO);
        if (carTemplateParamDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carTemplateParamRepository.existsById(carTemplateParamDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarTemplateParamDTO> result = carTemplateParamService.partialUpdate(carTemplateParamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carTemplateParamDTO.getId().toString())
        );
    }*/

    /**
     * {@code GET  /car-template-params} : get all the carTemplateParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carTemplateParams in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<CarTemplateParamDTO>> getAllCarTemplateParams(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.info("REST request to get a page of CarTemplateParams");
        Page<CarTemplateParamDTO> page = carTemplateParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-template-params/:id} : get the "id" carTemplateParam.
     *
     * @param id the id of the carTemplateParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carTemplateParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CarTemplateParamDTO> getCarTemplateParam(@PathVariable("id") Long id) {
        LOG.info("REST request to get CarTemplateParam : {}", id);
        Optional<CarTemplateParamDTO> carTemplateParamDTO = carTemplateParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carTemplateParamDTO);
    }

    /**
     * {@code DELETE  /car-template-params/:id} : delete the "id" carTemplateParam.
     *
     * @param id the id of the carTemplateParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCarTemplateParam(@PathVariable("id") Long id) {
        LOG.info("REST request to delete CarTemplateParam : {}", id);
        carTemplateParamService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
