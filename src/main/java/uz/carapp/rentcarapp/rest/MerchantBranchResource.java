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
import uz.carapp.rentcarapp.repository.MerchantBranchRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.MerchantBranchService;
import uz.carapp.rentcarapp.service.dto.MerchantBranchDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchEditDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchSaveDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.MerchantBranch}.
 */
@RestController
@RequestMapping("/api/v1/merchant-branches")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "MerchantBranch", description = "The MerchantBranch API.")
public class MerchantBranchResource {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantBranchResource.class);

    private static final String ENTITY_NAME = "merchantBranch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MerchantBranchService merchantBranchService;

    private final MerchantBranchRepository merchantBranchRepository;


    public MerchantBranchResource(MerchantBranchService merchantBranchService, MerchantBranchRepository merchantBranchRepository) {
        this.merchantBranchService = merchantBranchService;
        this.merchantBranchRepository = merchantBranchRepository;
    }

    /**
     * {@code POST  /merchant-branch} : Create a new merchantBranch.
     *
     * @param merchantBranchSaveDTO the merchantBranchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merchantDTO, or with status {@code 400 (Bad Request)} if the merchant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create merchantBranch")
    public ResponseEntity<MerchantBranchDTO> createMerchantBranch(@Valid @RequestBody MerchantBranchSaveDTO merchantBranchSaveDTO) throws URISyntaxException {
        LOG.info("REST request to save MerchantBranch : {}", merchantBranchSaveDTO);
        MerchantBranchDTO merchantBranchDTO = merchantBranchService.save(merchantBranchSaveDTO);
        return ResponseEntity.ok(merchantBranchDTO);
    }

    /**
     * {@code PATCH  /merchants/:id} : Partial updates given fields of an existing merchant, field will ignore if it is null
     *
     * @param merchantBranchEditDTO the merchantBranchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantDTO,
     * or with status {@code 400 (Bad Request)} if the merchantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the merchantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the merchantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "", consumes = { "application/json", "application/merge-patch+json" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Update merchantBranch")
    public ResponseEntity<Void> partialUpdateMerchant(
        @NotNull @RequestBody MerchantBranchEditDTO merchantBranchEditDTO
    ) throws URISyntaxException {
        LOG.info("REST request to partial update MerchantBranch partially : {}",  merchantBranchEditDTO);
        if (merchantBranchEditDTO.getId() == null) {
            throw new BadRequestCustomException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!merchantBranchRepository.existsById(merchantBranchEditDTO.getId())) {
            throw new BadRequestCustomException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        merchantBranchService.partialUpdate(merchantBranchEditDTO);

        return ResponseEntity.ok().build();
    }

    /**
     * {@code GET  /merchantBranches} : get all the merchantBranches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchantBranches in body.
     */
    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get list of merchantBranches")
    public ResponseEntity<List<MerchantBranchDTO>> getAllMerchantBranches(
            @RequestParam(value = "search", required = false) String search,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.info("REST request to get a page of Merchants by search:{}",search);
        Page<MerchantBranchDTO> page = merchantBranchService.findAll(search,pageable);
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
    @Operation(summary = "Get merchantBranch by id")
    public ResponseEntity<MerchantBranchDTO> getMerchantBranch(@PathVariable("id") Long id) {
        LOG.info("REST request to get Merchant Branch: {}", id);
        Optional<MerchantBranchDTO> merchantDTO = merchantBranchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantDTO);
    }

    /**
     * {@code DELETE  /merchants/:id} : delete the "id" merchant.
     *
     * @param id the id of the merchantBranchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     *//*
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Delete merchantBranch by id")
    public ResponseEntity<Void> deleteMerchantBranch(@PathVariable("id") Long id) {
        LOG.info("REST request to delete MerchantBranch : {}", id);
        merchantBranchService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
