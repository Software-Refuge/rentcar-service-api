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
public class CarParamEditDTO implements Serializable {

    private Long id;

    private String paramValueId;

    private String paramVal;
}
