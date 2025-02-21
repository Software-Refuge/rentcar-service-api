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
import uz.carapp.rentcarapp.repository.CarTemplateRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CarTemplateService;
import uz.carapp.rentcarapp.service.dto.CarTemplateDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateEditDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateSaveDTO;

import java.net.URISyntaxException;
import java.util.List;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.CarTemplate}.
 */
@RestController
@RequestMapping("/api/v1/car-templates")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class CarTemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarTemplateResource.class);

    private static final String ENTITY_NAME = "carTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarTemplateService carTemplateService;

    private final CarTemplateRepository carTemplateRepository;

    public CarTemplateResource(CarTemplateService carTemplateService, CarTemplateRepository carTemplateRepository) {
        this.carTemplateService = carTemplateService;
        this.carTemplateRepository = carTemplateRepository;
    }

    /**
     * {@code POST  /car-templates} : Create a new carTemplate.
     *
     * @param carTemplateSaveDTO the carTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carTemplateDTO, or with status {@code 400 (Bad Request)} if the carTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CarTemplateDTO> createCarTemplate(@RequestBody CarTemplateSaveDTO carTemplateSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save CarTemplate : {}", carTemplateSaveDTO);
        CarTemplateDTO carTemplateDTO = carTemplateService.save(carTemplateSaveDTO);
        return ResponseEntity.ok(carTemplateDTO);
    }

    /**
     * {@code PATCH  /car-templates/:id} : Partial updates given fields of an existing carTemplate, field will ignore if it is null
     *
     * @param carTemplateEditDTO the carTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the carTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CarTemplateDTO> partialUpdateCarTemplate(
        @RequestBody CarTemplateEditDTO carTemplateEditDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update CarTemplate partially :  {}", carTemplateEditDTO);
        if (carTemplateEditDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!carTemplateRepository.existsById(carTemplateEditDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarTemplateDTO> result = carTemplateService.partialUpdate(carTemplateEditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carTemplateEditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /car-templates} : get all the carTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carTemplates in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<CarTemplateDTO>> getAllCarTemplates(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of CarTemplates");
        Page<CarTemplateDTO> page = carTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-templates/:id} : get the "id" carTemplate.
     *
     * @param id the id of the carTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CarTemplateDTO> getCarTemplate(@PathVariable("id") Long id) {
        LOG.info("REST request to get CarTemplate : {}", id);
        Optional<CarTemplateDTO> carTemplateDTO = carTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carTemplateDTO);
    }

    /**
     * {@code DELETE  /car-templates/:id} : delete the "id" carTemplate.
     *
     * @param id the id of the carTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
/*    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCarTemplate(@PathVariable("id") Long id) {
        LOG.info("REST request to delete CarTemplate : {}", id);
        carTemplateService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
