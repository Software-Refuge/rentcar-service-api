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
import uz.carapp.rentcarapp.repository.ParamRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.ParamService;
import uz.carapp.rentcarapp.service.dto.ParamDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Param}.
 */
@RestController
@RequestMapping("/api/v1/params")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class ParamResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParamResource.class);

    private static final String ENTITY_NAME = "param";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamService paramService;

    private final ParamRepository paramRepository;

    public ParamResource(ParamService paramService, ParamRepository paramRepository) {
        this.paramService = paramService;
        this.paramRepository = paramRepository;
    }

    /**
     * {@code POST  /params} : Create a new param.
     *
     * @param paramDTO the paramDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramDTO, or with status {@code 400 (Bad Request)} if the param has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ParamDTO> createParam(@RequestBody ParamDTO paramDTO) throws URISyntaxException {
        LOG.info("REST request to save Param : {}", paramDTO);
        paramDTO = paramService.save(paramDTO);
        return ResponseEntity.created(new URI("/api/params/" + paramDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, paramDTO.getId().toString()))
            .body(paramDTO);
    }

    /**
     * {@code PATCH  /params/:id} : Partial updates given fields of an existing param, field will ignore if it is null
     *
     * @param paramDTO the paramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramDTO,
     * or with status {@code 400 (Bad Request)} if the paramDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paramDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ParamDTO> partialUpdateParam(
        @RequestBody ParamDTO paramDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update Param partially : {}", paramDTO);
        if (paramDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!paramRepository.existsById(paramDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParamDTO> result = paramService.partialUpdate(paramDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /params} : get all the params.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of params in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority(T(uz.carapp.rentcarapp.security.AuthoritiesConstants).ADMIN, T(uz.carapp.rentcarapp.security.AuthoritiesConstants).OWNER)")
    public ResponseEntity<List<ParamDTO>> getAllParams(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of Params");
        Page<ParamDTO> page = paramService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /params/:id} : get the "id" param.
     *
     * @param id the id of the paramDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ParamDTO> getParam(@PathVariable("id") Long id) {
        LOG.info("REST request to get Param : {}", id);
        Optional<ParamDTO> paramDTO = paramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramDTO);
    }

    /**
     * {@code DELETE  /params/:id} : delete the "id" param.
     *
     * @param id the id of the paramDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteParam(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Param : {}", id);
        paramService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
