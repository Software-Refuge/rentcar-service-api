package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.carapp.rentcarapp.repository.MerchantDocumentRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.MerchantDocumentService;
import uz.carapp.rentcarapp.service.dto.MerchantDocumentDTO;
import uz.carapp.rentcarapp.service.dto.MerchantDocumentSaveDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

/**
 * REST controller for managing {@link uz.carapp.rentcarapp.domain.MerchantDocument}.
 */
@RestController
@RequestMapping("/api/v1/merchant-documents")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
public class MerchantDocumentResource {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantDocumentResource.class);

    private static final String ENTITY_NAME = "merchantDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MerchantDocumentService merchantDocumentService;

    private final MerchantDocumentRepository merchantDocumentRepository;

    public MerchantDocumentResource(
        MerchantDocumentService merchantDocumentService,
        MerchantDocumentRepository merchantDocumentRepository
    ) {
        this.merchantDocumentService = merchantDocumentService;
        this.merchantDocumentRepository = merchantDocumentRepository;
    }

    /**
     * {@code POST  /merchant-documents} : Create a new merchantDocument.
     *
     * @param documentId the merchantDocumentDTO to create.
     * @param merchantId the merchantDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merchantDocumentDTO, or with status {@code 400 (Bad Request)} if the merchantDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Assign document to merchant")
    public ResponseEntity<MerchantDocumentDTO> createMerchantDocument(@RequestParam(value = "documentId") Long documentId,
                                                                      @RequestParam(value = "merchantId") Long merchantId)
        throws URISyntaxException {
        LOG.info("REST request to save MerchantDocument : {},{}", documentId,merchantId);
        MerchantDocumentSaveDTO build = MerchantDocumentSaveDTO.builder().documentId(documentId).merchantId(merchantId).build();
        MerchantDocumentDTO merchantDocumentDTO = merchantDocumentService.save(build);
        return ResponseEntity.ok()
            .body(merchantDocumentDTO);
    }

    /**
     * {@code GET  /merchant-documents} : get all the merchantDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchantDocuments in body.
     */
/*    @GetMapping("")
    public List<MerchantDocumentDTO> getAllMerchantDocuments() {
        LOG.debug("REST request to get all MerchantDocuments");
        return merchantDocumentService.findAll();
    }*/

    /**
     * {@code GET  /merchant-documents/:id} : get the "id" merchantDocument.
     *
     * @param id the id of the merchantDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the merchantDocumentDTO, or with status {@code 404 (Not Found)}.
     */
/*    @GetMapping("/{id}")
    public ResponseEntity<MerchantDocumentDTO> getMerchantDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MerchantDocument : {}", id);
        Optional<MerchantDocumentDTO> merchantDocumentDTO = merchantDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantDocumentDTO);
    }*/

    /**
     * {@code DELETE  /merchant-documents/:id} : delete the "id" merchantDocument.
     *
     * @param id the id of the merchantDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
/*    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchantDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MerchantDocument : {}", id);
        merchantDocumentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
