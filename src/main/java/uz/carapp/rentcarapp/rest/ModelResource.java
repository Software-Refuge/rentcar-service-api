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
import uz.carapp.rentcarapp.repository.ModelRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.ModelService;
import uz.carapp.rentcarapp.service.dto.ModelDTO;
import uz.carapp.rentcarapp.service.dto.ModelEditDTO;
import uz.carapp.rentcarapp.service.dto.ModelSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Model}.
 */
@RestController
@RequestMapping("/api/v1/models")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class ModelResource {

    private static final Logger LOG = LoggerFactory.getLogger(ModelResource.class);

    private static final String ENTITY_NAME = "model";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelService modelService;

    private final ModelRepository modelRepository;

    public ModelResource(ModelService modelService, ModelRepository modelRepository) {
        this.modelService = modelService;
        this.modelRepository = modelRepository;
    }

    /**
     * {@code POST  /models} : Create a new model.
     *
     * @param modelSaveDTO the modelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelDTO, or with status {@code 400 (Bad Request)} if the model has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ModelDTO> createModel(@RequestBody ModelSaveDTO modelSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save Model : {}", modelSaveDTO);
        ModelDTO modelDTO = modelService.save(modelSaveDTO);
        return ResponseEntity.ok(modelDTO);
    }

    /**
     * {@code PATCH  /models/:id} : Partial updates given fields of an existing model, field will ignore if it is null
     *
     * @param modelDTO the modelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelDTO,
     * or with status {@code 400 (Bad Request)} if the modelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ModelDTO> partialUpdateModel(
        @RequestBody ModelEditDTO modelEditDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update Model partially : {}", modelEditDTO);
        if (modelEditDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<ModelDTO> result = modelService.partialUpdate(modelEditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelEditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /models} : get all the models.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of models in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<ModelDTO>> getAllModels(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of Models");
        Page<ModelDTO> page = modelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /models/:id} : get the "id" model.
     *
     * @param id the id of the modelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ModelDTO> getModel(@PathVariable("id") Long id) {
        LOG.info("REST request to get Model : {}", id);
        Optional<ModelDTO> modelDTO = modelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelDTO);
    }

    /**
     * {@code DELETE  /models/:id} : delete the "id" model.
     *
     * @param id the id of the modelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteModel(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Model : {}", id);
        modelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
