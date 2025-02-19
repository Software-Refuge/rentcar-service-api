package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.ModelAttachment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class ModelAttachmentDTO implements Serializable {

    private Long id;

    private boolean isMain;

    private ModelDTO model;

    private AttachmentDTO attachment;
}
