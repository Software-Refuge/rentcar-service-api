package uz.carapp.rentcarapp.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.carapp.rentcarapp.domain.enumeration.AttachmentTypeEnum;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.service.AttachmentService;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@RequiredArgsConstructor
public class AttachmentResource {

    private final AttachmentService attachmentService;

    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

    /**
     * to save file
     * @param multipartFile
     * @param attTypeEnum
     * @return
     */
    @Operation(summary = "Save attachments ")
    @PostMapping(value = "/attachments/save-file", consumes ={MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority(T(uz.carapp.rentcarapp.security.AuthoritiesConstants).ADMIN, T(uz.carapp.rentcarapp.security.AuthoritiesConstants).OWNER)")
    public ResponseEntity<AttachmentDTO> saveFile(@RequestPart(value = "multipartFile") MultipartFile multipartFile,
                                                  @RequestParam(value = "attTypeEnum") AttachmentTypeEnum attTypeEnum) throws Exception {
        AttachmentDTO attachmentDTO = attachmentService.saveFile(multipartFile, attTypeEnum);
        return ResponseEntity.ok()
                .body(attachmentDTO);
    }
}
