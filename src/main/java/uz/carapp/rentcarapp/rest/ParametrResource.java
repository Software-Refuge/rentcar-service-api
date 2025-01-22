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
import uz.carapp.rentcarapp.repository.ParametrRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.ParametrService;
import uz.carapp.rentcarapp.service.dto.ParametrDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Parametr}.
 */
@RestController
@RequestMapping("/api/v1/parametrs")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Parameter", description = "The Parameter API.")
public class ParametrResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParametrResource.class);

    private static final String ENTITY_NAME = "parametr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametrService parametrService;

    private final ParametrRepository parametrRepository;

    public ParametrResource(ParametrService parametrService, ParametrRepository parametrRepository) {
        this.parametrService = parametrService;
        this.parametrRepository = parametrRepository;
    }

    /**
     * {@code POST  /parametrs} : Create a new parametr.
     *
     * @param parametrDTO the parametrDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametrDTO, or with status {@code 400 (Bad Request)} if the parametr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create Parameter")
    public ResponseEntity<ParametrDTO> createParametr(@Valid @RequestBody ParametrDTO parametrDTO) throws URISyntaxException {
        LOG.info("REST request to save Parametr : {}", parametrDTO);
        parametrDTO = parametrService.save(parametrDTO);
        return ResponseEntity.created(new URI("/api/parametrs/" + parametrDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, parametrDTO.getId().toString()))
            .body(parametrDTO);
    }

    /**
     * {@code PATCH  /parametrs/:id} : Partial updates given fields of an existing parametr, field will ignore if it is null
     *
     * @param id the id of the parametrDTO to save.
     * @param parametrDTO the parametrDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametrDTO,
     * or with status {@code 400 (Bad Request)} if the parametrDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parametrDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parametrDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update parameter")
    public ResponseEntity<ParametrDTO> partialUpdateParametr(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParametrDTO parametrDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update Parametr partially : {}, {}", id, parametrDTO);
        if (parametrDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!parametrRepository.existsById(id)) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParametrDTO> result = parametrService.partialUpdate(parametrDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametrDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parametrs} : get all the parametrs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametrs in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get list of parameter")
    public ResponseEntity<List<ParametrDTO>> getAllParametrs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of Parametrs");
        Page<ParametrDTO> page = parametrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parametrs/:id} : get the "id" parametr.
     *
     * @param id the id of the parametrDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametrDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get parameter by id")
    public ResponseEntity<ParametrDTO> getParametr(@PathVariable("id") Long id) {
        LOG.info("REST request to get Parametr : {}", id);
        Optional<ParametrDTO> parametrDTO = parametrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametrDTO);
    }

    /**
     * {@code DELETE  /parametrs/:id} : delete the "id" parametr.
     *
     * @param id the id of the parametrDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Delete parameter by id")
    public ResponseEntity<Void> deleteParametr(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Parametr : {}", id);
        parametrService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
