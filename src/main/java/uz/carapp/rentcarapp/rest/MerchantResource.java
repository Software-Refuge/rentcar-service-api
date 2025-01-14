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
import uz.carapp.rentcarapp.repository.MerchantRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.MerchantService;
import uz.carapp.rentcarapp.service.dto.MerchantDTO;
import uz.carapp.rentcarapp.service.dto.MerchantSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.Merchant}.
 */
@RestController
@RequestMapping("/api/v1/merchants")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Merchant", description = "The Merchant API.")
public class MerchantResource {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantResource.class);

    private static final String ENTITY_NAME = "merchant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MerchantService merchantService;

    private final MerchantRepository merchantRepository;

    public MerchantResource(MerchantService merchantService, MerchantRepository merchantRepository) {
        this.merchantService = merchantService;
        this.merchantRepository = merchantRepository;
    }

    /**
     * {@code POST  /merchants} : Create a new merchant.
     *
     * @param merchantSaveDTO the merchantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merchantDTO, or with status {@code 400 (Bad Request)} if the merchant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create merchant")
    public ResponseEntity<MerchantDTO> createMerchant(@Valid @RequestBody MerchantSaveDTO merchantSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save Merchant : {}", merchantSaveDTO);
        MerchantDTO merchantDTO = merchantService.save(merchantSaveDTO);
        return ResponseEntity.ok().body(merchantDTO);
    }

    /**
     * {@code PATCH  /merchants/:id} : Partial updates given fields of an existing merchant, field will ignore if it is null
     *
     * @param merchantDTO the merchantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantDTO,
     * or with status {@code 400 (Bad Request)} if the merchantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the merchantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the merchantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "", consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update merchant")
    public ResponseEntity<Void> partialUpdateMerchant(
        @NotNull @RequestBody MerchantDTO merchantDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update Merchant partially : {}",  merchantDTO);
        if (merchantDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!merchantRepository.existsById(merchantDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        merchantService.partialUpdate(merchantDTO);

        return ResponseEntity.ok().build();
    }

    /**
     * {@code GET  /merchants} : get all the merchants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchants in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get list of merchants")
    public ResponseEntity<List<MerchantDTO>> getAllMerchants(
            @RequestParam(value = "search", required = false) String search,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of Merchants by search:{}",search);
        Page<MerchantDTO> page = merchantService.findAll(search,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /merchants/:id} : get the "id" merchant.
     *
     * @param id the id of the merchantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the merchantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get merchant by id")
    public ResponseEntity<MerchantDTO> getMerchant(@PathVariable("id") Long id) {
        LOG.info("REST request to get Merchant : {}", id);
        Optional<MerchantDTO> merchantDTO = merchantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantDTO);
    }

    /**
     * {@code DELETE  /merchants/:id} : delete the "id" merchant.
     *
     * @param id the id of the merchantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     *//*
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Delete merchant by id")
    public ResponseEntity<Void> deleteMerchant(@PathVariable("id") Long id) {
        LOG.info("REST request to delete Merchant : {}", id);
        merchantService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
