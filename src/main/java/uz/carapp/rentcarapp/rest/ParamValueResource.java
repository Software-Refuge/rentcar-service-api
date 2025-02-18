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
import uz.carapp.rentcarapp.repository.ParamValueRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.ParamValueService;
import uz.carapp.rentcarapp.service.dto.ParamValueDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueEditDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueSaveDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.ParamValue}.
 */
@RestController
@RequestMapping("/api/v1/param-values")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class ParamValueResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParamValueResource.class);

    private static final String ENTITY_NAME = "paramValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamValueService paramValueService;

    private final ParamValueRepository paramValueRepository;

    public ParamValueResource(ParamValueService paramValueService, ParamValueRepository paramValueRepository) {
        this.paramValueService = paramValueService;
        this.paramValueRepository = paramValueRepository;
    }

    /**
     * {@code POST  /param-values} : Create a new paramValue.
     *
     * @param paramValueSaveDTO the paramValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramValueDTO, or with status {@code 400 (Bad Request)} if the paramValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ParamValueDTO> createParamValue(@RequestBody ParamValueSaveDTO paramValueSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save ParamValue : {}", paramValueSaveDTO);
        ParamValueDTO paramValueDTO = paramValueService.save(paramValueSaveDTO);
        return ResponseEntity.ok(paramValueDTO);
    }

    /**
     * {@code PATCH  /param-values/:id} : Partial updates given fields of an existing paramValue, field will ignore if it is null
     *
     * @param paramValueEditDTO the paramValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramValueDTO,
     * or with status {@code 400 (Bad Request)} if the paramValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paramValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paramValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ParamValueDTO> partialUpdateParamValue(
        @RequestBody ParamValueEditDTO paramValueEditDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update ParamValue partially :  {}",paramValueEditDTO);
        if (paramValueEditDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!paramValueRepository.existsById(paramValueEditDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParamValueDTO> result = paramValueService.partialUpdate(paramValueEditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramValueEditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /param-values} : get all the paramValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramValues in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority(T(uz.carapp.rentcarapp.security.AuthoritiesConstants).ADMIN, T(uz.carapp.rentcarapp.security.AuthoritiesConstants).OWNER)")
    @Operation(summary = "Get ParamValue list by paramId")
    public ResponseEntity<List<ParamValueDTO>> getAllParamValues(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                                 @RequestParam(value = "paramId") Long paramId) {
        LOG.info("REST request to get a page of ParamValues by paramId");
        Page<ParamValueDTO> page = paramValueService.findAll(pageable, paramId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /param-values/:id} : get the "id" paramValue.
     *
     * @param id the id of the paramValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ParamValueDTO> getParamValue(@PathVariable("id") Long id) {
        LOG.info("REST request to get ParamValue : {}", id);
        Optional<ParamValueDTO> paramValueDTO = paramValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramValueDTO);
    }

    /**
     * {@code DELETE  /param-values/:id} : delete the "id" paramValue.
     *
     * @param id the id of the paramValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteParamValue(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ParamValue : {}", id);
        paramValueService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
