package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import uz.carapp.rentcarapp.domain.enumeration.LanguageEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Translation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class TranslationDTO implements Serializable {

    private Long id;

    private String entityType;

    private Long entityId;

    private String fieldName;

    private LanguageEnum lang;

    private String value;

    private String description;

    // prettier-ignore
    @Override
    public String toString() {
        return "TranslationDTO{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", fieldName='" +getFieldName() + "'" +
            ", entityId=" + getEntityId() +
            ", lang='" + getLang() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
