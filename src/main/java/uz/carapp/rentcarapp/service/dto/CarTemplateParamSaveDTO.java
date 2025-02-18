package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarTemplateParam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarTemplateParamSaveDTO implements Serializable {

    private Long id;

    private String paramVal;

    private Long carTemplateId;

    private Long paramId;

    private Long paramValueId;
}
