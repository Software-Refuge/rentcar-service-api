package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarParam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarParamDTO implements Serializable {

    private Long id;

    private String paramItemValue;

    private String paramValue;

    private CarDTO car;

    private ParamDTO param;
}
