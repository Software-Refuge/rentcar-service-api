package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Attachment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class AttachmentDTO implements Serializable {

    private Long id;

    private String fileName;

    private Long fileSize;

    private String originalFileName;

    private String path;

    private String ext;
}
