package uz.carapp.rentcarapp.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.MerchantDocument} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
@Builder
public class MerchantDocumentDTO implements Serializable {

    private Long id;

    private MerchantDTO merchant;

    private DocumentDTO document;
}
