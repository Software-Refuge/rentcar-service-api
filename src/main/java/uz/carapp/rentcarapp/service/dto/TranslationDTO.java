package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.carapp.rentcarapp.domain.enumeration.LanguageEnum;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Translation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class TranslationDTO implements Serializable {

    private Long id;

    private String entityType;

    private Long entityId;

    private String name;

    private LanguageEnum lang;

    private String description;
}
