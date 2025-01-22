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
import uz.carapp.rentcarapp.repository.CategoryRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.CategoryService;
import uz.carapp.rentcarapp.service.dto.CategoryDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Category}.
 */
@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Categories", description = "The Category API.")
public class CategoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "category";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    public CategoryResource(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryDTO, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create Category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        LOG.info("REST request to save Category : {}", categoryDTO);
        categoryDTO = categoryService.save(categoryDTO);
        return ResponseEntity.created(new URI("/api/categories/" + categoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, categoryDTO.getId().toString()))
            .body(categoryDTO);
    }

    /**
     * {@code PATCH  /categories/:id} : Partial updates given fields of an existing category, field will ignore if it is null
     *
     * @param categoryDTO the categoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryDTO,
     * or with status {@code 400 (Bad Request)} if the categoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update category")
    public ResponseEntity<CategoryDTO> partialUpdateCategory(
        @NotNull @RequestBody CategoryDTO categoryDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update Category partially : {}", categoryDTO);
        if (categoryDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!categoryRepository.existsById(categoryDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryDTO> result = categoryService.partialUpdate(categoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get list of categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Categories");
        Page<CategoryDTO> page = categoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get category by id")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id) {
        LOG.info("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Delete category by id")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
