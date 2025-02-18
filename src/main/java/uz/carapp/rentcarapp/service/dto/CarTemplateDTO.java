package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarTemplate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class CarTemplateDTO implements Serializable {

    private Long id;

    private Boolean status;

    private ModelDTO model;

    // prettier-ignore
    @Override
    public String toString() {
        return "CarTemplateDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", model=" + getModel() +
            "}";
    }
}
