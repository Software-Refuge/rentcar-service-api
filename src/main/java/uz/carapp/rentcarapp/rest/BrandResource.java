package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.carapp.rentcarapp.repository.BrandRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.BrandService;
import uz.carapp.rentcarapp.service.dto.BrandDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Brand}.
 */
@RestController
@RequestMapping("/api/v1/brands")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Brand", description = "The Brand API.")
public class BrandResource {

    private static final Logger LOG = LoggerFactory.getLogger(BrandResource.class);

    private static final String ENTITY_NAME = "brand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BrandService brandService;

    private final BrandRepository brandRepository;

    public BrandResource(BrandService brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    /**
     * {@code POST  /brands} : Create a new brand.
     *
     * @param brandDTO the brandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new brandDTO, or with status {@code 400 (Bad Request)} if the brand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create brand")
    public ResponseEntity<BrandDTO> createBrand(@Valid @RequestBody BrandDTO brandDTO) throws URISyntaxException {
        LOG.info("REST request to save Brand : {}", brandDTO);
        brandDTO = brandService.save(brandDTO);
        return ResponseEntity.ok(brandDTO);
    }

    /**
     * {@code PATCH  /brands/:id} : Partial updates given fields of an existing brand, field will ignore if it is null
     *
     * @param brandDTO the brandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brandDTO,
     * or with status {@code 400 (Bad Request)} if the brandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the brandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the brandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update brand")
    public ResponseEntity<BrandDTO> partialUpdateBrand(
        @NotNull @RequestBody BrandDTO brandDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update Brand partially : {}", brandDTO);
        if (brandDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!brandRepository.existsById(brandDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BrandDTO> result = brandService.partialUpdate(brandDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, brandDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /brands} : get all the brands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brands in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get list of brands")
    public List<BrandDTO> getAllBrands() {
        LOG.info("REST request to get all Brands");
        return brandService.findAll();
    }

    /**
     * {@code GET  /brands/:id} : get the "id" brand.
     *
     * @param id the id of the brandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the brandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get brand by id")
    public ResponseEntity<BrandDTO> getBrand(@PathVariable("id") Long id) {
        LOG.info("REST request to get Brand : {}", id);
        Optional<BrandDTO> brandDTO = brandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(brandDTO);
    }

    /**
     * {@code DELETE  /brands/:id} : delete the "id" brand.
     *
     * @param id the id of the brandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     *//*
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Delete brand by id")
    public ResponseEntity<Void> deleteBrand(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Brand : {}", id);
        brandService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
