package uz.carapp.rentcarapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Model} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class ModelDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean status;

    private BrandDTO brand;

    @JsonIgnoreProperties(value = "model")
    List<ModelAttachmentDTO> modelAttachment;
}
