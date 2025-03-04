package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.carapp.rentcarapp.domain.enumeration.DocTypeEnum;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Document} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class DocumentSaveDTO implements Serializable {

    private Long id;

    private String name;

    private DocTypeEnum docType;

    private Instant givenDate;

    private Instant issuedDate;

    private Boolean docStatus;
}
