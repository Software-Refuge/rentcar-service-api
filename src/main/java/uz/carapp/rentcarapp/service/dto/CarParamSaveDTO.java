package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarParam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarParamSaveDTO implements Serializable {

    private Long id;

    private Long paramValueId;

    private String paramVal;

    private Long carId;

    private Long paramId;
}
