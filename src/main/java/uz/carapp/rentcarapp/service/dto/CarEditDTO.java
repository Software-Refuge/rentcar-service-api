package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Car} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarEditDTO implements Serializable {

    private Long id;

    private String stateNumberPlate;

    private Integer deposit;
}
