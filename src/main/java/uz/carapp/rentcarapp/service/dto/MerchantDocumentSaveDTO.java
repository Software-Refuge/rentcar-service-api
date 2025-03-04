package uz.carapp.rentcarapp.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.MerchantDocument} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
@Builder
public class MerchantDocumentSaveDTO implements Serializable {

    private Long id;

    private Long merchantId;

    private Long documentId;
}
