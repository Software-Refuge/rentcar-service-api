package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Car} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarSaveDTO implements Serializable {

    private Long id;

    private String stateNumberPlate;

    private Integer deposit;

    private Long modelId;

    private Long merchantId;

    private Long merchantBranchId;
}
